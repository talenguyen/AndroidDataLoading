package vn.tale.androiddataloading;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by Giang Nguyen on 4/3/16.
 */
public class NoErrorTransformerTest {
  private NoErrorTransformer<Object> noErrorTransformer;
  private TestSubscriber<Object> testSubscriber;

  @Before public void setUp() throws Exception {
    noErrorTransformer = new NoErrorTransformer<>();
    testSubscriber = new TestSubscriber<>();
  }

  @Test public void testCallMethod_errorStream_shouldReceiveEmpty() throws Exception {
    final Observable<Object> errorStream = Observable.error(new RuntimeException());
    errorStream.compose(noErrorTransformer).subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertNoValues();
  }
}