package com.udacity.gradle.builditbigger.testing;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AsyncTaskAndroidTest {

    private CountDownLatch mLatch = null;
    private String mResult = null;
    private Exception mException = null;
    private Activity mActivity;

    @Before
    public void setUpTest() {
        mLatch = new CountDownLatch(1);

        Thread thread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                Handler mHandler = new Handler() {
                    public void instantiateActivity() {
                        mActivity = new Activity();
                    }
                };

                Looper.loop();
            }
        };
        thread.start();
    }

    @Test
    public void testEndpointsAsyncTask() throws InterruptedException {
        new EndpointsAsyncTask(mActivity).setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result, Exception exception) {
                mResult = result;
                mException = exception;
                mLatch.countDown();
            }
        }).execute();
        mLatch.await();

        assertNull(mException);
        assertNotNull(mResult);
        assertFalse(TextUtils.isEmpty(mResult));
        assertNotEquals(mResult, "Connection refused");
    }
}
