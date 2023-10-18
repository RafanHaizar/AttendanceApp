package androidx.lifecycle;

public class MutableLiveData<T> extends LiveData<T> {
    public MutableLiveData(T value) {
        super(value);
    }

    public MutableLiveData() {
    }

    public void postValue(T value) {
        super.postValue(value);
    }

    public void setValue(T value) {
        super.setValue(value);
    }
}
