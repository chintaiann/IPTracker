import { useState } from "react";
import { Button } from "@mui/material";
import { TextField, MenuItem } from "@mui/material";
import PageTable from "../component/PageTable"
import { Typography } from "@mui/material";
import {protocols} from '../util/constants'
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SendIcon from '@mui/icons-material/Send';
import ResultTable from "../component/ResultTable";
import axiosAuth from "../util/axiosAuth";
import { useEffect } from "react";
import Pagination from '@mui/material/Pagination';

export default function SingleQuery() { 
    const [data,setData] = useState([]); 
    const [pageNumber,setPageNumber] = useState(1)
    const [totalPages,setTotalPages] = useState(0)

    //first time we always get first page
    const handleSubmitIP = ( event) => { 
        axiosAuth(
            {
                method:'GET', 
                url:`/getAll/` + 0,
            }
        ).then(function (response){ 
            console.log("Page: " + response.data.pageable.pageNumber);
            console.log("Total Pages: " + response.data.totalPages)
            setTotalPages(response.data.totalPages)
            setPageNumber(1);
            setData(response.data.content)
            // setData(response.data.response);
        }
        ).catch (function (error){ 
            toast.error(error.response.data.errorMessage);
        })         

    }
    const handlePaging = ( event) => { 
        const pageToFind = pageNumber-1
        axiosAuth(
            {
                method:'GET', 
                url:`/getAll/` + pageToFind,
            }
        ).then(function (response){ 
            console.log("Page: " + response.data.pageable.pageNumber);
            console.log("Total Pages: " + response.data.totalPages)
            setTotalPages(response.data.totalPages)
            setData(response.data.content)
        }
        ).catch (function (error){ 
            toast.error(error.response.data.errorMessage);
        })         

    }
    //everytime our page number changes, call the api again to get correct page 
    useEffect(handlePaging,[pageNumber,totalPages]);
    const handlePageChange = (event,value) => { 
        setPageNumber(value); 
    }

    return (
        <div id="Page" className="Page">
            <Typography sx={{textDecoration: 'underline', color:"#650000"}} variant="h4">Test</Typography>
                <div>
                    { (totalPages === 0) 
                    ?  <div></div>
                    :  <div>          
                            <Pagination count={totalPages} page={pageNumber} onChange={handlePageChange}></Pagination>
                            <Typography>Showing page {pageNumber} of {totalPages}</Typography>
                        </div>
                    } 
                </div>
            
            <Button  endIcon={<SendIcon />} size="medium" id="singleQuerySubmit" variant="contained" onClick={e=>{handleSubmitIP()}}>Query IPv6/IPv4</Button> 
        <PageTable data={data}></PageTable>
        <ToastContainer id="Toast" />

        </div>
    ) 
}
//sample of a page
//content: all the ip info 
// "pageable": {
//     "sort": {
//         "empty": true,
//         "unsorted": true,
//         "sorted": false
//     },
//     "offset": 30,
//     "pageNumber": 3,
//     "pageSize": 10,
//     "paged": true,
//     "unpaged": false
// },
// "totalPages": 1000,
// "totalElements": 10000,
// "last": false,
// "size": 10,
// "number": 3,
// "sort": {
//     "empty": true,
//     "unsorted": true,
//     "sorted": false
// },
// "numberOfElements": 10,
// "first": false,
// "empty": false
// }