package com.delains.ui.threading;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.function.Function;

public class ServiceTask<PARAM,T> extends Service<T> {

    /*
    public ServiceTask(Function<PARAM, T> function, PARAM param) {
        this.function = function;
        this.param = param;

        createTask();
        start();

    }
    */

    public Function<PARAM, T> function;
    private PARAM param;

    // private Function functionOnSuc

    /*
    public void functionOnSucceed(Function function, Object param){
        function.apply(param);
    }*/

    public void createFunction(Function<PARAM, T> function, PARAM param){
        this.function = function;
        this.param = param;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void succeeded() {
        super.succeeded();
    }


    @Override
    protected Task<T> createTask() {

        return new Task<T>() {
            @Override
            protected T call() throws Exception {
                function.apply(param);
                return super.getValue();
            }
        };
    }

}
