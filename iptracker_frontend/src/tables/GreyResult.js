import React, { useState, useRef, useEffect, useMemo, useCallback} from 'react';
import { createRoot } from 'react-dom/client';
import { AgGridReact } from 'ag-grid-react'; // the AG Grid React Component
import 'ag-grid-enterprise';
import 'ag-grid-community/styles/ag-grid.css'; // Core grid CSS, always needed
import 'ag-grid-community/styles/ag-theme-alpine.css'; // Optional theme CSS

export default function GreyResult({rowData}) {

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
    [  {field: "ip",headerName:"IP"},  {field: "actor"},    {field: "asn"},    {field: "bot"},    {field: "category"},    {field: "city"},    {field: "classification"},    {field: "country"},    {field: "cve"},    {field: "first_seen"},    {field: "hassh",cellRenderer:ja3Renderer},    {field: "ja3", cellRenderer: ja3Renderer },    {field: "last_seen"},    {field: "organization"},    {field: "os"},    {field: "region"},    {field: "scan",cellRenderer:scanRenderer},    {field: "seen"},    {field: "spoofable"},    {field: "tags"},    {field: "useragents"},    {field: "vpn"},    {field: "vpn_service"},    {field: "webpaths"}]
 )
 ;

 // DefaultColDef sets props common to all Columns
 const defaultColDef = useMemo( ()=> ({
     sortable: true,
     filter: true,
     resizable : true,
     flex:1,
     minWidth: 120

   }));

function scanRenderer(params){ 
    const data = params.data[params.colDef.field];
    let output = ""
    if (typeof data === 'object' && data !== null) {
        data.forEach((obj, index) => {
            output += `Port: ${obj.port}, Protocol: ${obj.protocol}`;
            if (index < data.length - 1) {
              output += " | ";
            }
          });
}
    return output
}

function ja3Renderer(params) {
    const data = params.data[params.colDef.field];
    let output = ""
    if (typeof data === 'object' && data !== null) {
        data.forEach((obj, index) => {
            output += `Port: ${obj.port}, Fingerprint: ${obj.fingerprint}`;
            if (index < data.length - 1) {
              output += " | ";
            }
          });
}
    return output
}

   const onGridReady = (params) => {
    const { columnApi } = params;
    // const columnValues = columnDefs.map(column => column.field);
    // console.log(columnValues)
    // columnApi.autoSizeColumns(columnValues); // pass the column ids to auto-size
};

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

