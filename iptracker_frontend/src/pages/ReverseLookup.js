import { useState, useEffect} from "react";
import {Typography,MenuItem,Button,TextField}from "@mui/material";
import axios from "axios";
import { protocols } from "../util/constants";
import { countries, usage_type, ipConstants } from "../util/constants";
import {ToastContainer,toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SelectField from "../util/SelectField";
import ReverseResultTable from "../tables/ReverseResultTable";
import axiosAuth from "../util/axiosAuth";
import Pagination from '@mui/material/Pagination';
import SearchIcon from '@mui/icons-material/Search';
import { pageSizeList } from "../util/constants";

export default function ReverseLookup() { 
    const [protocol,setProtocol] = useState('IPv4'); 
    const[filteredIP, setFilteredIP] = useState([]);
    const [filters,setFilters] = useState(ipConstants);
    const [isp,setIsp] = useState("");

    const [pageNumber,setPageNumber] = useState(1)
    const [totalPages,setTotalPages] = useState(0)
    const [page,setPage] = useState(true);
    const [pageSize,setPageSize] = useState(10);






    const handleChange = (name,value) => { 
        // const copy =  _.cloneDeep(filters);
        if (name === 'Country') { 
            setFilters(prev => ({...prev, countryName: value}))
        }

        if (name === 'Usage Type') { 
            setFilters(prev => ({...prev, usageType: value}))
        }

        if (name === 'Page Size') { 
            setPageSize(value);
            setPage(1)
        }
    }

    const handleReverseLookUp = (event) => {
        let formData = new FormData();
        formData.append('country_name',filters.countryName);
        formData.append('isp',isp);
        formData.append('usage_type',filters.usageType);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axiosAuth.post(
            `/reverseLookUp/${protocol}/0/${pageSize}` , 
            formData, 
            config
        ).then(response => {
            console.log("Page: " + response.data.response.pageable.pageNumber);
            console.log("Total Pages: " + response.data.response.totalPages)
            setTotalPages(response.data.response.totalPages)
            setPageNumber(1);
            setFilteredIP(response.data.response.content)
            setPage(true);

            } 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }

    const handlePaging = (event) =>  {
        let formData = new FormData();
        formData.append('country_name',filters.countryName);
        formData.append('isp',isp);
        formData.append('usage_type',filters.usageType);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axiosAuth.post(
            `/reverseLookUp/${protocol}/${pageNumber-1}/${pageSize}` , 
            formData, 
            config
        ).then(response => {
            console.log("Page: " + response.data.response.pageable.pageNumber);
            console.log("Total Pages: " + response.data.response.totalPages)
            setTotalPages(response.data.response.totalPages)
            setFilteredIP(response.data.response.content)
            } 
        ).catch ( error => toast.error(error.response.data.errorMessage));
    }

    useEffect( () => { 
        setPage(false)
        setPageNumber(1)
        setTotalPages(0)
        setFilters(ipConstants); 
        setFilteredIP([]); 
        setIsp("");
    },[protocol])

    //if page is true, we have done our first search 
    useEffect( () => { 
        if (page) { 
            handlePaging();
        }

        
    },[pageNumber,totalPages,pageSize])
    // useEffect(handlePaging,[pageNumber,totalPages]);
    const handlePageChange = (event,value) => { 
        setPageNumber(value); 
    }


    




    return (
        <div className="Page">
            <Typography style={{"marginTop":"20px"}} variant="h4">Reverse Lookup</Typography>
            <div className="selectProtocol">
                 <TextField
                id="selectProtocol"
                select
                defaultValue="IPv4"
                onChange={e=>{setProtocol(e.target.value)}}

            >
                {protocols.map((option)=> (
                    <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
                ))}
            </TextField>
            </div>

        
            <div className="selectFields">
            <SelectField protocol={protocol} list={countries} name='Country' onChangeFunction={handleChange} /> 
            <SelectField protocol={protocol}  list={usage_type} name='Usage Type' onChangeFunction={handleChange} /> 
            {/* <SelectField protocol={protocol}  list={isp} name='isp' onChangeFunction={handleChange} /> */}
            <TextField value={isp} onChange={e=>{setIsp(e.target.value)}} variant="outlined" label="Isp"></TextField>
            <Button endIcon={<SearchIcon/>}color="steelBlue" size="medium" variant="contained"  onClick={e=>{handleReverseLookUp()}}>Search</Button> 

            </div>
            <div>
                    { (totalPages === 0) 
                    ?  <div></div>
                    :  <div className="results">
                        <div className="pagination">          
                            <Pagination variant="outlined" color="primary" count={totalPages} page={pageNumber} onChange={handlePageChange}></Pagination>
                            <Typography>Showing page {pageNumber} of {totalPages}</Typography>
                            <SelectField width="120" size="small" defaultValue={pageSize}list={pageSizeList} name="Page Size" onChangeFunction={handleChange}></SelectField>
                        </div>
                        <ReverseResultTable data={filteredIP}/>                        
                        <div className="pagination"> 
                            <Typography>Showing page {pageNumber} of {totalPages}</Typography>  
                            <Pagination variant="outlined" color="primary" count={totalPages} page={pageNumber} onChange={handlePageChange}></Pagination>
                        </div>
                        </div>
                    }
             </div>           
            <ToastContainer/>
        </div>
    )
}