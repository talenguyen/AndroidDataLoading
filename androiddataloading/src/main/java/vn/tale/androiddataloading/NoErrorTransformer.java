package vn.tale.androiddataloading;

import rx.Observable;

/**
 * Implementation of {@link Observable.Transformer} to transform an {@link Observable} into another
 * {@link Observable} object that never emit error.
 * @param <T> Data type.
 */
public class NoErrorTransformer<T> implements Observable.Transformer<T, T> {

  @Override public Observable<T> call(Observable<T> sourceStream) {
    return sourceStream.onErrorResumeNext(Observable.<T>empty());
  }
}
