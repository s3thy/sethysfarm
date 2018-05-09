package controller;

import app.ErstelleDaten;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static funktionen.Werkzeuge.format;

public class ViewControllerTabelle
{
   @FXML

   public TextArea txt_ausgabe;
   public TextArea txt_ausgabe1;
   public TextArea txt_anzahl;
   public TextArea txt_console;

   public TableView table_getreide;
   public TableColumn table_getreide_name;
   public TableColumn table_getreide_hoehe;

   void init()
   {
      new ErstelleDaten().createFarm();

      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));

      tabelleFuellen();
   }

   private String getMyData(String pflanzenart)
   {
      StringBuilder sb = new StringBuilder();
      if( pflanzenart.toLowerCase().equals("mais") )
      {
         for( Pflanze pflanze : maisFeld )
         {
            sb.append("Mais").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            sb.append("Weizen").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      return sb.toString();
   }

   private void fillTable(String pflanzenart)
   {
      table_getreide = new TableView();

      TableColumn nameCol = null;
      TableColumn hoeheCol = null;

      int lfn = 0;

      if( pflanzenart.toLowerCase().equals("mais") )
      {
         for( Pflanze pflanze : maisFeld )
         {
            lfn++;
            nameCol = new TableColumn("Mais_" + lfn);
            hoeheCol = new TableColumn("Hoehe " + pflanze.getHoehe());

         }
         table_getreide.getColumns().addAll(nameCol, hoeheCol);
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            lfn++;
            nameCol = new TableColumn("Weizen_" + lfn);
            hoeheCol = new TableColumn("Hoehe " + pflanze.getHoehe());
         }
         table_getreide.getColumns().addAll(nameCol, hoeheCol);
      }
   }

   private void tabelleFuellen()
   {
      TableView<Pflanze> table = new TableView<Pflanze>();

      // Create column Plant (Data type of String).
      TableColumn<Pflanze, String> table_getreide_name = new TableColumn<Pflanze, String>("Pflanzenname");

      // Create column Plantsize (Data type of String).
      TableColumn<Pflanze, String> table_getreide_hoehe = new TableColumn<Pflanze, String>("Pflanzenhoehe");

      table_getreide.getColumns().addAll(table_getreide_name, table_getreide_hoehe);

      // Defines how to fill data for each cell.
      // Get value from property of UserAccount. .
      table_getreide_name.setCellValueFactory(new PropertyValueFactory<>("Pflanzenname"));
      table_getreide_hoehe.setCellValueFactory(new PropertyValueFactory<>("Pflanzenhoehe"));

      // Display row data
      ObservableList<Pflanze> list = getPlantsList();
      table_getreide.setItems(list);

      table_getreide.getColumns().addAll(table_getreide_name, table_getreide_hoehe);
   }

   private ObservableList<Pflanze> getPlantsList()
   {

      Pflanze user1 = new Mais(5.5);
      Pflanze user2 = new Mais(6.5);

      ObservableList<Pflanze> list = FXCollections.observableArrayList(user1, user2);
      return list;
   }


}