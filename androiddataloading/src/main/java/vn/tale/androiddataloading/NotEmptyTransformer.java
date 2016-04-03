package vn.tale.androiddataloading;

import java.util.Collection;
import java.util.NoSuchElementException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Implementation of {@link Observable.Transformer} to transform an {@link Observable} into another
 * {@link Observable} object that never be an empty stream.
 * @param <T> Data type.
 */
public class NotEmptyTransformer<T> implements Observable.Transformer<T, T> {

  @Override public Observable<T> call(Observable<T> sourceStream) {
    final Observable<T> notNullOrEmptyListStream = sourceStream.filter(new Func1<T, Boolean>() {
      @Override public Boolean call(T t) {
        if (t == null) {
          return false;
        }
        if (t instanceof Collection) {
          final Collection collection = (Collection) t;
          return collection.size() > 0;
        }
        return true;
      }
    });
    final Observable<T> emptyReplacementStream = Observable.error(new NoSuchElementException());
    return notNullOrEmptyListStream.switchIfEmpty(emptyReplacementStream);
  }
}
