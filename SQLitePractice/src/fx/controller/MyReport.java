package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import alert.ESCAlert;
import db.MyReportData;
import db.ReportDAO;
import fx.AppMainTheCheat;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MyReport implements Initializable {

	@FXML private BorderPane myReportPane;
	@FXML private TableView<MyReportData> tViewContent;
	@FXML private Button btnDelete;
	@FXML private Label lblChk;
	@FXML private TableColumn<MyReportData , String> tColcName,tColcTitle;
	@FXML private TableColumn<MyReportData, Integer> tColcnum;
	//@FXML private TableColumn<MyReportData, Boolean> tColChk= new TableColumn<MyReportData,Boolean>("check"); 
	@FXML private TableColumn<MyReportData, Boolean> tColChk;
	private ReportDAO rDao = new ReportDAO();
	private String userId;
    private Vector<Integer> checkedNum = new Vector<Integer>();
    private Vector<String> checkedName = new Vector<String>();
    private String lblText = new String();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Platform.runLater(()->getDatas());
		btnDelete.setOnAction(e->deleteAction());
	}

	public int deleteAction() {
		if(checkedNum.size()>0) {
			ESCAlert alert = new ESCAlert();
			String alTitle = Integer.toString(checkedNum.size())+"??? ?????????  ??????????????? ";
			String alHeader = "";
			for(String name:checkedName) {
				alHeader += name+",";
			}
			alHeader += "??? ??????????????? ";
			String alContent = "????????? ??????????????? ";
			
			boolean isDelete = alert.costomAlert(userId, alTitle, alHeader, alContent);
			if(isDelete) {
				int[] del = new int[checkedNum.size()];
				int i = 0;
				for(Integer check : checkedNum) {
					del[i] = check;
					i++;
				}
				int result = rDao.deleteReporting(del);
				if(result == 1) {
					alert.basicAlertShow("????????????" , checkedNum.size()+"?????? ?????????????????? ");
					try {
						//?????? ?????? ???????????????????????? ??????????????? 				
						 FXMLLoader loader = new FXMLLoader();
						 loader.setLocation(AppMainTheCheat.class.getResource("fxml/Main.fxml"));
						 Parent main =loader.load();
							
						Main mainCon = loader.getController();
						mainCon.setUser(userId);
						mainCon.viewMyReport(userId);
						//?????? ?????? + ??? ???????????? ???????????? ????????? ?????? 
						Scene mainScene = new Scene(main);
						Stage stage = (Stage)btnDelete.getScene().getWindow();
						stage.setScene(mainScene);
						
					} catch(IOException e2) {
						e2.printStackTrace();
						System.out.println("??????");
					}
				}
				else {
					alert.basicAlertShow("????????????", "??????!");
				}
			}
		
		}
		else {
			ESCAlert al = new ESCAlert();
			String alTitle = "?????? ?????? ????????? ????????????????????????";
			String alHeader = "?????? ?????? ";
			al.basicAlertShow(alTitle, alHeader);
		}
		
		return 0;
	}
	
	public void getDatas() {
		
		ObservableList<MyReportData> datas = FXCollections.observableArrayList();
		MyReportData[] reports = rDao.getMyReportInfo(userId);
		
		
		if(reports != null) {
			for(MyReportData my : reports) {
				System.out.println(my.getcId());
			}
		
			
			//????????? ???????????? ????????? ??????????????? (??????????????? ???????????? ????????? ?????? ????????? ??????)
			tColcName.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("cId"));
			tColcnum.setCellValueFactory(new PropertyValueFactory<MyReportData, Integer>("num"));
			tColcTitle.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("title")); 
	        // ??????????????? Cell??? ??????
	      
		    tColChk.setCellValueFactory(new PropertyValueFactory<MyReportData,Boolean>("check"));
		    tColChk.setCellFactory(column -> new TableCell<MyReportData, Boolean>(){
	        
		    public void updateItem(Boolean check, boolean empty) {
	             super.updateItem(check, empty);
	             if (check == null || empty) {
	              setGraphic(null);
	             } else {
	              CheckBox box = new CheckBox();
	              BooleanProperty checked = (BooleanProperty)column.getCellObservableValue(getIndex());
	              
	              MyReportData my = (MyReportData)column.getTableView().getItems().get(getIndex());
	       
	              if (checked.get()){
	            	  if(!checkedNum.contains(my.getNum())) {
	            		  checkedNum.add(my.getNum());
	            		  checkedName.add(my.getcId());
	            		  lblText = "";
	            		  for(int i=0; i < checkedName.size();i++) {
	            			  lblText += checkedNum.get(i).toString()+"??? :"+checkedName.get(i)+"    ";
	            		  }
	            		  lblChk.setText(lblText);
	            	  }
	            	  
	            	  
	            
	              } else {
	            	  if(checkedNum.contains(my.getNum())) {
	            		  for(int i =0; i<checkedNum.size();i++) {
		            		  if(checkedNum.get(i) == my.getNum()) {
			            		  checkedNum.remove(i);
			            		  checkedName.remove(i);
			            		  
			            	  }
		            	  }
	            		  lblText = "";
	            		  for(int i=0; i < checkedName.size();i++) {
	            			  lblText += checkedNum.get(i).toString()+"??? :"+checkedName.get(i)+"    ";
	            		  }
	            		  lblChk.setText(lblText);
	            	  }
	            	  System.out.println(my.getNum()+"???");
	            	  System.out.println(my.getcId()+" is Unchecked!");
	              }
	              box.setSelected(checked.get());
	              System.out.println(checkedNum.size()+"?????????");
	              for(Integer ch : checkedNum) {
	            	  System.out.println(ch.intValue()+"???");
	              }
	              box.selectedProperty().bindBidirectional(checked);
	             setGraphic(box);
	             }
	            }
	           }
       );
      	
	      	     
           tViewContent.setEditable(true);
          
           datas.setAll(reports);
		   tViewContent.setItems(datas);
	    }
	
	}	
	
	public void setUser(String userId) {
		this.userId =  userId;
	}
	
}
