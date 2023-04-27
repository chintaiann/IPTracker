import React from "react"
import { useTable } from 'react-table'
import { useState , useEffect} from "react"
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
//pass in data response from API call and hiddenColumns 
export default function Table({data,hiddenColumns}) { 
    const [hidden,setHidden] = useState([])
    const filters = [  { "label": "IP", "columnName": "ip" },  { "label": "Country", "columnName": "country" },  { "label": "City", "columnName": "city" },  { "label": "Region", "columnName": "region" },  { "label": "Category", "columnName": "category" },  { "label": "Actor", "columnName": "actor" },  { "label": "Class", "columnName": "classification" },  { "label": "Operation System", "columnName": "os" },  { "label": "ASN", "columnName": "asn" },  { "label": "CVE", "columnName": "cve" },  { "label": "First Seen", "columnName": "first_seen" },  { "label": "Last Seen", "columnName": "last_seen" },  { "label": "Bot", "columnName": "bot" },  { "label": "Organization", "columnName": "organization" },  { "label": "Seen", "columnName": "seen" },  { "label": "Spoofable", "columnName": "spoofable" },  { "label": "VPN", "columnName": "vpn" },  { "label": "HASSH", "columnName": "hassh" },  { "label": "JA3", "columnName": "ja3" },  { "label": "Scan", "columnName": "scan" },  { "label": "Tags", "columnName": "tags" },  { "label": "User Agents", "columnName": "useragents" },  { "label": "Web Paths", "columnName": "webpaths" }]
    const columns = React.useMemo(
        () => [
            {
                Header: "IP Results (GreyNoise)", 
                columns: [
                    {
                        Header: "IP",
                        accessor:"ip"
                    },
                    {
                        Header: "Country",
                        accessor:"country"
                    }, 
                    {
                        Header: "City",
                        accessor:"city"
                    },                     
                    {
                        Header: "Region",
                        accessor:"region"
                    },                     
                    {
                        Header: "Category",
                        accessor:"category"
                    },                     {
                        Header: "Actor",
                        accessor:"actor"
                    },                     {
                        Header: "Class",
                        accessor:"classification"
                    },                     {
                        Header: "Operation System",
                        accessor:"os"
                    }, 
                    {
                        Header: "ASN",
                        accessor:"asn"
                    },
                    {
                      Header: "CVE",
                      accessor: "cve",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            {row.original.cve &&
                              row.original.cve.map((item,i) => 
                              <div key={i}>
                                {item}
                              </div>)
                            }
                          </div>
                        )
                      }

                    },
                    {
                      Header: "First Seen",
                      accessor:"first_seen"
                    }, 
                    {
                      Header: "Last Seen",
                      accessor:"last_seen"
                    }, 
                    {
                      Header: "Bot",
                      accessor:"bot",
                      Cell: ({row}) => { 
                        if (row.original.bot!==null) { 
                          return ( 
                          <div>
                            {row.original.bot ? 
                            <div>True</div> : 
                            <div>False</div>}
                          </div>
                          
                        )
                        }
                        
                      }
                    }, 
                    {
                      Header: "Organization",
                      accessor:"organization"
                    }, 
                    {
                      Header: "Seen",
                      accessor:"seen",
                      Cell: ({row}) => { 
                        if (row.original.seen!==null) { 
                          return ( 
                          <div>
                            {row.original.seen ? 
                            <div>True</div> : 
                            <div>False</div>}
                          </div>
                
                        )
                        }
                        
                      }
                    }, 
                    {
                      Header: "Spoofable",
                      accessor:"spoofable",
                      Cell: ({row}) => { 
                        if (row.original.spoofable!==null) {
                           return ( 
                          <div>
                            {row.original.spoofable ? 
                            <div>True</div> : 
                            <div>False</div>}
                          </div>
                          
                        )
                        } 
                       
                      }
                    }, 

                    {
                      Header: "VPN",
                      accessor:"vpn",
                      Cell: ({row}) => { 
                  
                        if (row.original.vpn!==null){
                            return ( 
                          <div>
                            
                            {row.original.vpn ? 
                            <div>True</div> : 
                            <div>False</div>}
                          </div>
                          
                        ) 
                        }
                        
                      }
                    }, 
                    {
                      Header: "HASSH",
                      accessor: "hassh",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            { row.original.hassh &&
                              row.original.hassh.map((item,i) => 
                              <div key={i}>
                                Port: {item.port} | Fingerprint: {item.fingerprint}
                              </div>)
                            }
                          </div>
                        )
                      }
                    }, 
                    {
                      Header: "JA3",
                      accessor: "ja3",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            {row.original.ja3 &&
                              row.original.ja3.map((item,i) => 
                              <div key={i}>
                                Port: {item.port} | Fingerprint: {item.fingerprint}
                              </div>)
                            }
                          </div>
                        )
                      }
                     }, 

                    {
                      Header: "Scan",
                      accessor: "scan",
                      Cell: ({row}) => { 
                        console.log(row)
                        return ( 
                          <div>
                            { row.original.scan &&
                              row.original.scan.map((item,i) => 
                              <div key={i}>
                                Port: {item.port} | Protocol: {item.protocol}
                              </div>)
                            }
                          </div>
                        )
                      }
                    }, 
                    {
                      Header: "Tags",
                      accessor: "tags",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            { row.original.tags &&
                              row.original.tags.map((item,i) => 
                              <div key={i}>
                                {item}
                              </div>)
                            }
                          </div>
                        )
                      }
                    }, 
                    {
                      Header: "User Agents",
                      accessor: "useragents",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            { 
                              row.original.useragents &&
                              row.original.useragents.map((item,i) => 
                              <div key={i}>
                                {item}
                              </div>)
                            }
                          </div>
                        )
                      }
                    }, 
                    {
                      Header: "Web Paths",
                      accessor: "webpaths",
                      Cell: ({row}) => { 
                        return ( 
                          <div>
                            {row.original.webpaths &&
                              row.original.webpaths.map((item,i) => 
                              <div key={i}>
                                {item}
                              </div>)
                            }
                          </div>
                        )
                      }
                    }, 



                ]
            }
        ],[]
    )
    const tableInstance = useTable({ columns, data})
    
    const {
       getTableProps,
       getTableBodyProps,
       headerGroups,
       rows,
       prepareRow,
       setHiddenColumns
     } = tableInstance

    const toggleFilter = (columnName) => { 
      if (hidden.includes(columnName)){ 
        console.log("deleting " + columnName)
        setHiddenColumns(hidden.filter(function(fields) { 
          return fields !== columnName
        }))
        setHidden(hidden.filter(function(fields) { 
          return fields !== columnName
        }))
      }
      if (!hidden.includes(columnName)) {
        console.log("adding " + columnName)
        setHiddenColumns(oldArray => [...oldArray,columnName])
        setHidden(oldArray => [...oldArray,columnName])
      }
    }


     return (
      <div className="greynoiseTable" >
          <FormGroup className="columnFilters" row>
            {
              filters.map((item,i) => 
              <FormControlLabel control={<Checkbox defaultChecked onChange={e=>{toggleFilter(item.columnName)}} />} label={item.label} />
              )
            }
          </FormGroup>        
     <div className="resultTable">
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
                         <td  style={{"borderWidth":"1px",'borderColor':"#aaaaaa", 'borderStyle':'solid', 'padding':'10px'}} {...cell.getCellProps()}>
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
     </div>

     </div>
         )
        
}