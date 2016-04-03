package vn.tale.androiddataloading;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by Giang Nguyen on 4/3/16.
 */
public class NotEmptyTransformerTest {
  private NotEmptyTransformer<String> notEmptyTransformer;
  private TestSubscriber<Object> testSubscriber;

  @Before public void setUp() throws Exception {
    notEmptyTransformer = new NotEmptyTransformer<>();
    testSubscriber = new TestSubscriber<>();
  }

  @Test public void testCallMethod_errorStream_shouldReceiveError() throws Exception {
    final RuntimeException expectedError = new RuntimeException("");
    final Observable<String> errorStream = Observable.error(expectedError);

    final Observable<String> notEmptyStream = errorStream.compose(notEmptyTransformer);
    notEmptyStream.subscribe(testSubscriber);

    testSubscriber.assertError(expectedError);
  }

  @Test public void testCallMethod_emptyStream_shouldReceiveNoSuchElementError() throws Exception {
    final Observable<String> emptyStream = Observable.empty();

    final Observable<String> notEmptyStream = emptyStream.compose(notEmptyTransformer);
    notEmptyStream.subscribe(testSubscriber);

    testSubscriber.assertError(NoSuchElementException.class);
  }

  @Test public void testCallMethod_nullDataStream_shouldReceiveNoSuchElementError() throws Exception {
    final Observable<String> emptyStream = Observable.just(null);

    final Observable<String> notEmptyStream = emptyStream.compose(notEmptyTransformer);
    notEmptyStream.subscribe(testSubscriber);

    testSubscriber.assertError(NoSuchElementException.class);
  }


  @Test public void testCallMethod_emptyListDataStream_shouldReceiveNoSuchElementError() throws Exception {
    final Observable<List<String>> emptyStream = Observable.just(Collections.<String>emptyList());

    final NotEmptyTransformer<List<String>> notEmptyTransformer = new NotEmptyTransformer<>();
    final Observable<List<String>> notEmptyStream = emptyStream.compose(notEmptyTransformer);
    notEmptyStream.subscribe(testSubscriber);

    testSubscriber.assertError(NoSuchElementException.class);
  }

  @Test public void testCallMethod_dataStream_shouldReceiveData() throws Exception {
    final Observable<String> dataStream = Observable.just("Data");

    final Observable<String> notEmptyStream = dataStream.compose(notEmptyTransformer);
    notEmptyStream.subscribe(testSubscriber);

    testSubscriber.assertValues("Data");
  }
}