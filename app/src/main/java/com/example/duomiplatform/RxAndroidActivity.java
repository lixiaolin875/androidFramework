package com.example.duomiplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.operators.observable.ObservableCreate;


public class RxAndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);

        loadData();
    }

    Observer<String> msub = new Observer<String>() {


        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
//            ToastView toastView = new ToastView();
//            toastView.showCenter(s, LYApplication.getApplication());

            Toast toast = Toast.makeText(LYApplication.getApplication(), s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };


    public void loadData() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Thread.sleep(5000);
                e.onNext("哈哈哈哈哈");
                e.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(msub);
    }


}