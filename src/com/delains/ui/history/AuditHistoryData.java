package com.delains.ui.history;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.model.history.AuditHistory;
import com.delains.model.history.AuditHistoryView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuditHistoryData {
    public static ObservableList<AuditHistory> theData =
            FXCollections.observableArrayList(AuditHistoryHibernation.findAllAuditHistorysWithRefreshing());

//    public static void addAuditHistoryToTable(AuditHistory auditHistory){
//
//        System.out.println("adding...");
//        AuditHistoryView auditHistoryView = new AuditHistoryView();
//        auditHistoryView.setDate(auditHistory.getDate());
//        auditHistoryView.setUserName(auditHistory.getUserId().getUserName());
//        auditHistoryView.setAction(auditHistory.getAction());
//        theData.add(auditHistoryView);
//
//    }

}
