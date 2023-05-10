import React, { useState, useRef, useEffect, useMemo, useCallback} from 'react';
import { createRoot } from 'react-dom/client';
import { AgGridReact } from 'ag-grid-react'; // the AG Grid React Component
import 'ag-grid-enterprise';
import 'ag-grid-community/styles/ag-grid.css'; // Core grid CSS, always needed
import 'ag-grid-community/styles/ag-theme-alpine.css'; // Optional theme CSS

export default function ReverseResult({rowData}) {

 const gridRef = useRef(); // Optional - for accessing Grid's API
 const [quickFilterText, setQuickFilterText] = useState("");

 function onQuickFilterChanged(event) {
   setQuickFilterText(event.target.value);
 }

 const gridOptions = {
    enableFilter: true,
    // other options...
  };
 // Each Column Definition results in one Column.
 const [columnDefs, setColumnDefs] = useState(
    [    {field: "ipRange.gte",headerName:"From"}, {field:"ipRange.lte",headerName:"To"} ,  {field: "countryName"},    {field: "cityName"},    {field: "regionName"},    {field: "isp"},    {field: "usageType"},    {field: "latitude"},    {field: "longitude"}]

 )
 ;

 // DefaultColDef sets props common to all Columns
 const defaultColDef = useMemo( ()=> ({
     sortable: true,
     filter: true,
     resizable : true,
     flex:1,
     minWidth:120
   }));

 // Example of consuming Grid Event
 const cellClickedListener = useCallback( event => {
   console.log('cellClicked', event);
 }, []);

 // Example load data from server

 // Example using Grid's API
 const buttonListener = useCallback( e => {
   gridRef.current.api.deselectAll();
 }, []);

 const sideBar = useMemo(() => {
    return {
      toolPanels: ['columns'],
    };
  }, []);



 return (
    <div>
      <input
        type="text"
        placeholder="Search..."
        value={quickFilterText}
        onChange={onQuickFilterChanged}
      />        <div className="ag-theme-alpine" style={{width: 1000, height: 500}}>
        <AgGridReact ref={gridRef} sideBar={sideBar} rowData={rowData}  columnDefs={columnDefs} defaultColDef={defaultColDef} animateRows={true} // Optional - set to 'true' to have rows animate when sorted
            rowSelection='multiple'  onCellClicked={cellClickedListener} gridOptions={gridOptions}
            quickFilterText={quickFilterText}/>
        </div>
    </div>

 );
};

