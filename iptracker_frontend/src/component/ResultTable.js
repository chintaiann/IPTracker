import { Typography } from "@mui/material";
import React from "react"
import { tableHeaders } from "../util/constants";
import { useTable } from 'react-table'

export default function Table({data}) { 


    const columns = React.useMemo(
        () => [
            {
                Header: "IP Results", 
                columns: [
                    {
                        Header: "IP",
                        accessor:"ip"
                    },
                    {
                        Header: "Country",
                        accessor:"country_name"
                    }, 
                    {
                        Header: "City",
                        accessor:"city_name"
                    },                     {
                        Header: "Region",
                        accessor:"region_name"
                    },                     {
                        Header: "ISP",
                        accessor:"isp"
                    },                     {
                        Header: "Usage Type",
                        accessor:"usage_type"
                    },                     {
                        Header: "Latitude",
                        accessor:"latitude"
                    },                     {
                        Header: "Longitude",
                        accessor:"longitude"
                    }, 

                ]
            }
        ],[]
    )

    const tableInstance = useTable({ columns, data })
    
    const {
       getTableProps,
       getTableBodyProps,
       headerGroups,
       rows,
       prepareRow,
     } = tableInstance


     return (
           // apply the table props
           <table style={{"marginTop":"15px" ,"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} {...getTableProps()}>
             <thead  style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}}>
               {// Loop over the header rows
               headerGroups.map(headerGroup => (
                 // Apply the header row props
                 <tr style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} {...headerGroup.getHeaderGroupProps()}>
                   {// Loop over the headers in each row
                   headerGroup.headers.map(column => (
                     // Apply the header cell props
                     <th  style={{"padding":"5px" ,"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid'}} {...column.getHeaderProps()}>
                       {// Render the header
                       column.render('Header')}
                    </th>
                   ))}
                 </tr>
               ))}
             </thead>
             {/* Apply the table body props */}
            <tbody  {...getTableBodyProps()}>
               {// Loop over the table rows
               rows.map(row => {
                 // Prepare the row for display
                 prepareRow(row)
                 return (
                   // Apply the row props
                   <tr {...row.getRowProps()}>
                     {// Loop over the rows cells
                     row.cells.map(cell => {
                       // Apply the cell props
                       return (
                         <td  style={{"borderWidth":"1px", 'borderColor':"#aaaaaa", 'borderStyle':'solid', 'padding':'10px'}} {...cell.getCellProps()}>
                           {// Render the cell contents
                           cell.render('Cell')}
                         </td>
                       )
                     })}
                   </tr>
                 )
               })}
             </tbody>
           </table>
         )
        
}