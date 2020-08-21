package com.delains.model.history;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class AuditHistoryView {

    private  ObjectProperty<BigDecimal> id ;
    private  StringProperty date ;
    private  StringProperty action ;
    private  StringProperty userName ;

    public AuditHistoryView() {
        this(null,null,null,null);
    }

    public AuditHistoryView(BigDecimal id, String date, String action, String userName) {
        this.id = new SimpleObjectProperty<>(id);
        this.date = new SimpleStringProperty(date);
        this.action = new SimpleStringProperty(action);
        this.userName = new SimpleStringProperty(userName);
    }

    public BigDecimal getId() {
        return id.get();
    }

    public ObjectProperty<BigDecimal> idProperty() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAction() {
        return action.get();
    }

    public StringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    @Override
    public String toString() {
        return "AuditHistoryView{" +
                "id=" + id +
                ", date=" + date +
                ", action=" + action +
                ", userName=" + userName +
                '}';
    }
}
