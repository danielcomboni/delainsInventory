package com.delains.ui.history;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.model.history.AuditHistory;
import com.delains.model.history.AuditHistoryView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class AuditHistoryManipulation {

	private static ObservableList<AuditHistoryView> auditHistoryViews;
	public static ObservableList<AuditHistoryView> data = getAuditHistoryViews();
	public static ObservableList<AuditHistoryView> getAuditHistoryViews() {
		return auditHistoryViews;
	}

	public static void setAuditHistoryViews(ObservableList<AuditHistoryView> auditHistoryViews) {
		AuditHistoryManipulation.auditHistoryViews = auditHistoryViews;
	}

	public static ObservableList<AuditHistoryView> mapAuditHistoryView(){

		for(AuditHistory auditHistory : AuditHistoryHibernation.findAllAuditHistorysWithRefreshing()){
			addAuditHistoryViewToCollection(auditHistory);
		}

		return getAuditHistoryViews();
	}

	private static AuditHistoryView setView(AuditHistory auditHistory){
		AuditHistoryView auditHistoryView = null;
		if (auditHistory.getUserId() == null){
			auditHistoryView =
					new AuditHistoryView(
							auditHistory.getId(), auditHistory.getDate(), auditHistory.getAction(),
							"not existing");
		}
		else {
			auditHistoryView =
					new AuditHistoryView(
							auditHistory.getId(), auditHistory.getDate(), auditHistory.getAction(),
							auditHistory.getUserId().getUserName());

		}
		return auditHistoryView;
	}

	public static AuditHistoryView addAuditHistoryViewToCollection(AuditHistory auditHistory){

		AuditHistoryView auditHistoryView;

		if (getAuditHistoryViews() == null || getAuditHistoryViews().isEmpty() ){
			ObservableList<AuditHistoryView> auditHistoryViews = FXCollections.observableArrayList();

			auditHistoryView = setView(auditHistory);

			auditHistoryViews.add(auditHistoryView);
			setAuditHistoryViews(auditHistoryViews);

		}else {

			auditHistoryView = setView(auditHistory);

			getAuditHistoryViews().add(auditHistoryView);

		}

		return auditHistoryView;
	}


//	public static ObservableList<AuditHistory> data = AuditHistoryHibernation.findAllAuditHistorysWithRefreshing() ;
//
//	public static void addAuditHistoryToTable(AuditHistory auditHistory){
//		Service<Void> service = new Service<Void>() {
//			@Override
//			protected Task<Void> createTask() {
//				return new Task<Void>() {
//					@Override
//					protected Void call() throws Exception {
//
//						data.add(auditHistory);
//
//						return null;
//					}
//				};
//			}
//		};
//		service.start();
//	}
//
//	private static AddColumnsToTable < AuditHistory > addColumnsToTable;

//	public static TableView < AuditHistory > getTableView() {
//
////		AuditHistory auditHistory = new AuditHistory();
////
////		addColumnsToTable = new AddColumnsToTable <>( auditHistory );
////
////		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "date" ), "date" );
////		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "action" ), "action" );
////		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "user" ), "userid" );
//
////		return addColumnsToTable.getTableViewGeneric().getTableView();
//
//		return setTable();
//	}

//	public static void populateTableWithRefreshingData() {
//		addColumnsToTable.populateTableRefresh( data );
//	}

//	public static void populateTableWithRefreshing() {
//		addColumnsToTable.populateTableRefresh( data );
//	}

	/*
        public static void populatetableWithoutRefreshing() {
            addColumnsToTable
                    .populateTableWithoutRefreshing( AuditHistoryHibernation.findAllAuditHistorysWithoutRefreshing() );
        }
    */
	private static TableView<AuditHistory> tableView = new TableView<>();


//	public static TableView<AuditHistory> setTable(){

//
//
//		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
//		tableView.setTableMenuButtonVisible( true );
//
//
//
//		data.stream().forEach(e -> {
//
//
//			System.out.println( "act:" +e.getAction());
//
////		if (e.getUserid().getUserName() != null){
////			System.out.println("flat: " + e.getUserid().getUserName());
////		}
//
//		});
//
//		if (!data.isEmpty()){
//
//			tableView.setItems(data);
//
//		}
//
//		tableView.getColumns().addAll(columnDate,columnAction,columnUser);
//
//		columnAction.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDate()));
//
//		columnAction.setCellValueFactory(new PropertyValueFactory<>("action"));
//
//		columnUser.setCellValueFactory(param -> {
//
//			String name;
//			if (param.getValue().getUserid() != null){
//				name = param.getValue().getUserid().getUserName();
//			}else{
//				name = "user deleted";
//			}
//
////		SimpleObjectProperty<User> user = param.getValue().useridProperty();
////		System.out.println(user.getName());
////		return new SimpleStringProperty(userObjectProperty.getValue().getUserName());
//			return new SimpleStringProperty(name);
//		});
//
//		return tableView;
//
//	}
//
//
//
//	private static TableColumn < AuditHistory, String > setColumnValueFactory(
//			TableColumn < AuditHistory, String > column, String decideValue ) {
//
//		column.setCellValueFactory(
//				new Callback < TableColumn.CellDataFeatures < AuditHistory, String >, ObservableValue < String > >() {
//
//					@Override
//					public ObservableValue < String > call( CellDataFeatures < AuditHistory, String > param ) {
//
//						AuditHistory pc = param.getValue();
//
//						String val = null;
//
//						if ( decideValue.equalsIgnoreCase( "date" ) ) {
//							val = pc.getDate();
//						}
//
//						if ( decideValue.equalsIgnoreCase( "action" ) ) {
//
//							val = pc.getAction();
//
//						}
//
//						if ( decideValue.equalsIgnoreCase( "userid" ) ) {
//							if ( pc.getUserid() != null ) {
//								val = pc.getUserid().getUserName();
//							} else {
//								val = null;
//							}
//
//						}
//
//						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );
//
//						StringProperty property = simpleStringProperty;
//
//						return property;
//
//					}
//				} );
//
//		return column;
//
//	}

}
