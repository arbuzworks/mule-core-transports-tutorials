# PostgreSQL JDBC Connector

The JDBC Connector allows you to send and receive messages with a database using the JDBC protocol. Common usage includes retrieving, inserting, updating, and deleting database records, as well as invoking stored procedures (e.g., to create new tables dynamically).

### Contents 

[Prerequisites](#prerequisites)  
[Step 1: Prepare database](#step-1-prepare-database)  
[Step 2: Create Demo Project](#step-2-create-demo-project)  
[Step 3: Configure application](#step-3-configure-application)  
[Step 4: Create System Info flow](#step-4-create-system-info-flow)  
[Step 5: Run project](#step-5-run-project)  
[Step 6: Test System Info flow](#step-6-test-system-info-flow)  
[Step 7: Create listItems flow](#step-7-create-listitems-flow)  
[Step 8: Test listItems flow](#step-8-test-listitems-flow)  
[Step 9: Create createItem flow](#step-9-create-createitem-flow)  
[Step 10: Test createItem flow](#step-10-test-createitem-flow)  
[Step 11: Create flows for the rest actions (Update, Delete, Find by ID)](#step-11-create-flows-for-the-rest-actions-update-delete-find-by-id)  
[Step 12: Test updateItem, deleteItem and findById flows](#step-12-test-updateitem-deleteitem-and-findbyid-flows)  
[Other Resources](#other-resources)  


### Prerequisites

In order to build and run this project you'll need:

*   [MuleStudio](http://www.mulesoft.org/download-mule-esb-community-edition).
*   Installed [PostgreSQL Server](http://postgresapp.com) database. In order to use the PostgreSQL Server database in our application you have to know:    
        * host    
        * port    
        * username    
        * password     
*   A JDBC Driver for PostgreSQL Server. You can download it from [PostgreSQL JDBC Driver](http://jdbc.postgresql.org/download/postgresql-9.2-1003.jdbc4.jar)

### Step 1: Prepare database

First, open the built-in **psql** application.


Create a database with a command:   
```sql
CREATE DATABASE mulesoft;
```

Connect to the database:
```
\connect mulesoft
```

Create a table:    
```sql
CREATE TABLE demo_tbl 
(
  id SERIAL,
  title VARCHAR(100),
  description VARCHAR(100)
);   
```    
### Step 2: Create Demo Project

*    Run Mule Studio and select **File \> New \> Mule Project** menu item.  
*    Type **demo** as a project name and click **Next**.  

![Create Demo project](images/step2-1.png)

*    Then click **Next**.

![Create Demo project](images/step2-2.png)

*    And finally click **Finish**.

![Create Demo project](images/step2-3.png)

### Step 3: Configure application
    
We need to provide values for the database parameters. Open **src/main/app/flows/mule-app.properties** file and add the following lines: 
```ini
db.connection_string=jdbc:postgresql://localhost:5432/mulesoft 
db.user=username    
db.password=password    
```

![Configure application](images/step3-1.png)    

To be able to access the PostgreSQL database, we need to add a PostgreSQL JDBC client jar file to our build path. Right-click your project, select **Build Path** and choose **Add External Archives**. Choose **postgresql-9.2-1003.jdbc4.jar** file from the **/lib** directory of the demo project. 

![Configure application](images/step3-2.png)

##### Configuration of PostgreSQL Data Source

Open **flows/demo.mflow** file. For configuration of PostgreSQL Data Source select **Global Elements** tab, click **Create** button and using the filter find and select  **PostgreSQL Data Source**. Click **OK**. You will see a window for **PostgreSQL Data Source** configuration, adjust the fields as displayed on the following image.

![Configure application](images/step3-3.png)

##### Configuration of the JDBC Connector

Click **Create** button and using the filter find and select the **Database** connector. Then click **OK**. You will see a window for **JDBC endpoint** configuration. In the **Database Specific** dropdown select **PostgreSQL_Data_Source** which we configured earlier.

![Configure application](images/step3-4.png)

We will implement the main actions for the work with the database in our application. They are: sysinfo, create, retrieve, update and delete items. In this window select the **Queries** tab and add a query to retrieve all items from the table. For this purpose click **+** button, set **Query Key** as **selectAll** and **Query** as

```sql
SELECT * FROM demo_tbl
```

![Configure application](images/step3-5.png)

In the following list you can see all the queries to be used in our application. Add them in the same way:

* selectAll:  
```sql
SELECT * FROM demo_tbl
```

* insertItem:    
```sql
INSERT INTO demo_tbl (title, description) VALUES (#[message.inboundProperties['title']], #[message.inboundProperties['description']])
```
* updateItem:     
```sql
UPDATE demo_tbl SET title=#[message.inboundProperties['title']], description=#[message.inboundProperties['description']] WHERE id=CAST(#[message.inboundProperties['id']] AS integer)
```

* deleteItem:    
```sql
DELETE FROM demo_tbl WHERE id=CAST(#[message.inboundProperties['id']] AS integer)
```

* findById:    
```sql
SELECT * FROM demo_tbl  WHERE id=CAST(#[message.inboundProperties['id']] AS integer)
```

* sysinfo:    
```sql
SELECT version()
```

![Configure application](images/step3-6.png)

After you have added all the queries click **OK**.

### Step 4: Create System Info flow

*    Switch to the **Message Flow** tab in the flow editor.
*    Add a new flow by dragging it from the Palette.
*    Double click the new flow to open its properties and rename it to **sysInfo**. Click **OK**.

![Create System Info flow](images/step4-1.png)

*    Drag **HTTP Endpoint** to the flow. Double click it to show its properties and adjust them as displayed on the following image.

![Create System Info flow](images/step4-2.png)

*    Drag **Database Endpoint** to the flow. Double click it to show its properties. Set **Display name** as **Select all items**, choose **request-response** as **Exchange Patterns**.
*    Switch to the **References** tab and in the **Connector Reference** dropdown select **Database** which we configured earlier on Step 3.

![Create System Info flow](images/step4-3-1.png)

*    Switch to the **General** tab. Now in the **SQL Statement** panel in the **Key** dropdown list you can see all the keys of queries you added when configuring the JDBC Connector. Select **sysinfo** and click **OK**.
 
![Create System Info flow](images/step4-3-2.png)

*    Drag **Object to JSON** transformer to the flow. It does not need any additional settings.

![Create System Info flow](images/step4-4.png)

*    Save the flow.

### Step 5: Run project

*    Right Click **src/main/app/demo.xml \> Run As/Mule Application**.

![Run Project](images/step5-1.png) 

*    Check the console to see when the application starts.  

You should see a log message on the console:  
 
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
    + Started app 'demo'                                       +    
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   

### Step 6: Test System Info flow

*    When the application is started, open your browser and point it to [http://localhost:8081/sysinfo](http://localhost:8081/sysinfo).

*    You should see the database system information in JSON format.

![Test System Info flow](images/step6-1.png) 

*    Stop Mule server. 

### Step 7: Create listItems flow 

*    Switch to the **Message Flow** tab in the flow editor.
*    Add a new flow by dragging it from the Palette.
*    Double click the new flow to open its properties and rename it to **listItems**. Click **OK**.

![Create List Items flow](images/step7-1.png)

*    Drag **HTTP Endpoint** to the flow. Double click it to show its properties and adjust them as displayed on the following image.

![Create List Items flow](images/step7-2.png)

*    Drag **Database Endpoint** to the flow. Double click it to show its properties. Set **Display name** as **Select all items**, choose **request-response** as **Exchange Patterns**.
*    Switch to the **References** tab and in the **Connector Reference** dropdown select **Database** which we configured earlier on Step 3.

![Create List Items flow](images/step7-3-1.png)

*    Switch to the **General** tab. Now in the **SQL Statement** panel in the **Key** dropdown list you can see all the keys of queries you added when configuring the JDBC Connector. Select **selectAll** and click **OK**.
 
![Create List Items flow](images/step7-3-2.png)

*    Drag **Object to JSON** transformer to the flow. It does not need any additional settings.

![Create List Items flow](images/step7-4.png)

*    Save the flow.


### Step 8: Test listItems flow

*    When the application is started, open your browser and point it to [http://localhost:8081/list](http://localhost:8081/list).

*    You should see the string of empty JSON array because our table in the database is still empty.

![Test List Items flow](images/step8-1.png) 

*    Stop Mule server.  

### Step 9: Create createItem flow

*    Add a new flow by dragging it from the Palette.
*    Double click the new flow to open its properties and rename it to **createItem**. Click **OK**.

![Create Item flow](images/step9-1.png)

*    Drag **HTTP Endpoint** to the flow. Double click it to show its properties and adjust them as displayed on the following image.

![Create Item flow](images/step9-2.png)

*    Drag **Database Endpoint** to the flow. Double click it to show its properties. Set **Display name** as **Insert new item**. Switch to the **References** tab and in the **Connector Reference** dropdown select **Database** which we configured earlier on Step 3.
*    Switch to the **General** tab. In the **SQL Statement** panel in the **Key** dropdown list select **insertItem** and click **OK**.

![Create Item flow](images/step9-3.png)

*    Drag another **Database Endpoint** to the flow and configure it in the same way as the **Create listItems flow** endpoint on Step 7.
*    Drag **Object to JSON** transformer to the flow.

![Create Item flow](images/step9-4.png)

*    Save the flow.

### Step 10: Test createItem flow

*    Run the project.
*    Open your browser and point it to [http://localhost:8081/create?title=TestTitle&description=TestDescription](http://localhost:8081/create?title=TestTitle&description=TestDescription).
*    You should see the JSON array which contains the created item based on the GET request parameters.

![Test Create Item flow](images/step10-1.png)

*    Point your browser to [http://localhost:8081/list](http://localhost:8081/list) and you should see all the items of the **demo_tbl** table.

*    Stop Mule server.  

### Step 11: Create flows for the rest actions (Update, Delete, Find by ID)

*   In the same way as we created **createItem flow** you have to create **updateItem flow**, **deleteItem flow** and **findById flow**. You have to use **updateItem**, **deleteItem** and **findById** query keys to Update, Delete and Find by ID actions in the flows.

![Create rest flows](images/step11-1.png)

### Step 12: Test updateItem, deleteItem and findById flows

*    Run the project.
*    Test each flow:
    *    Retrieve one item: [http://localhost:8081/find?id=1](http://localhost:8081/find?id=1)
    *    Update the item: [http://localhost:8081/update?id=1&title=NewTitle&description=NewDescription](http://localhost:8081/update?id=1&title=NewTitle&description=NewDescription)
    *    Select all items: [http://localhost:8081/list](http://localhost:8081/list)
    *    Delete the item: [http://localhost:8081/delete?id=1](http://localhost:8081/delete?id=1)
*    Stop Mule server.   

### Other Resources

For more information on:

- Mule AnyPoint® connectors, please visit [http://www.mulesoft.org/connectors](http://www.mulesoft.org/connectors)
- Mule platform and how to build Mule apps, please visit [http://www.mulesoft.org/documentation/display/current/Home](http://www.mulesoft.org/documentation/display/current/Home)


