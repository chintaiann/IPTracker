import { useState , useEffect} from "react";
import { Button } from "@mui/material";
import { TextField,MenuItem,Typography } from "@mui/material";
import { protocols } from "../util/constants";
import {ToastContainer,toast} from 'react-toastify';
import ResultTable from "../legacy/ResultTable";
import React from 'react';
import axiosAuth from "../util/axiosAuth";
import Pagination from '@mui/material/Pagination';
import SearchIcon from "@mui/icons-material/Search";
import ArticleIcon from '@mui/icons-material/Article';
import DataObjectIcon from '@mui/icons-material/DataObject';
import PopUp from "../component/PopUp";
import SelectField from "../util/SelectField";
import { pageSizeList } from "../util/constants";
import SourceView from "../component/SourceView";
import { sources } from "../util/constants";
import GreynoiseResult from "../legacy/GreynoiseResult"
import IP2LResult from "../tables/IP2LResult";
import GreyResult from "../tables/GreyResult";
export default function BulkQuery() { 
    const [ip,setip] = useState('');
    const [submittedIp,setSubmittedIp] = useState([]);
    const [response,setResponse] = useState([]);
    const [protocol,setProtocol] = useState('IPv4'); 
    const [jsonField,setjsonField] = useState(''); 
    const [seen,setSeen] = useState(false);

    const hiddenTextInput = React.useRef(null);
    const hiddenJsonInput = React.useRef(null);
    const [pageNumber,setPageNumber] = useState(1)
    const [totalPages,setTotalPages] = useState(0)
    const [pageSize,setPageSize] = useState(10);
    const [source,setSource] = useState(sources[0])

    const [didRender, setDidRender]= useState(false);

    const togglePop = () => {
        setSeen(!seen);
       };
    const handleChange = (name,value) => { 
        if (name === 'Page Size') { 
            setPageSize(value);
            setPageNumber(1)
        }
    }



    const handlePageChange = (event,value) => { 
        setPageNumber(value); 
    }
    const textButtonClick = (event) => { 
        hiddenTextInput.current.click();
    }
    const jsonButtonClick = (event) => {
        hiddenJsonInput.current.click();
    }

    let fileReader;

    //handle switching of sources
    const handleSourceChange = (event) => {
        
        if (submittedIp.length > 0) { 
            console.log("handlSourceChange : Bulk Query ")
            const submit = submittedIp.slice(0,pageSize);
            let formData = new FormData();
            formData.append('ipList',submit);
            const config = { 
                headers : { 'content-type':'multipart/form-data'}
            }
            axiosAuth.post(
                `/bulkQuery/${protocol}/${source}` , 
                formData, 
                config
            ).then(response => {
                console.log(response.data.response)
                setPageNumber(1)
                setTotalPages(Math.ceil(submittedIp.length/pageSize));
                setResponse(response.data.response)
            } 
            ).catch ( error => toast.error(error.response.data.errorMessage));
        }
    }

    //first submit for select field
    const handleBulkQuery = (event) => {
        // console.log("handleBulkQuery : Bulk QUery ");
        // const ipList = ip.split(",");
        // setSubmittedIp(ipList);
        // const submit = ipList.slice(0,pageSize);
        // let formData = new FormData();
        // formData.append('ipList',submit);
        // const config = { 
        //     headers : { 'content-type':'multipart/form-data'}
        // }
        // axiosAuth.post(
        //     `/bulkQuery/${protocol}/${source}` , 
        //     formData, 
        //     config
        // ).then(response => {
        //     setPageNumber(1)
        //     setTotalPages(Math.ceil(ipList.length/pageSize));
        //     setResponse(response.data.response)
        // } 
        // ).catch ( error => toast.error(error.response.data.errorMessage));
        const ipList_temp = ip.split(",");

        const ipList = [...new Set(ipList_temp)];
        setPageNumber(1)
        setSubmittedIp(ipList); 
    }

    //api calls when we change pages, use submittedIP 
    const handlePaging = (event) => {
        const start = (pageNumber-1) * pageSize
        const end = pageSize*pageNumber
        const submit = submittedIp.slice(start,end)
        let formData = new FormData();
        formData.append('ipList',submit);
        const config = { 
            headers : { 'content-type':'multipart/form-data'}
        }
        axiosAuth.post(
            `/bulkQuery/${protocol}/${source}` , 
            formData, 
            config
        ).then(response => {
            setResponse(response.data.response)
            setTotalPages(Math.ceil(submittedIp.length/pageSize))
        } 
        ).catch (error => toast.error(error.response.data.errorMessage));
    }

    //first submit for text/json upload
    const handleBulkUpload =(ipString) => {
        const ipList_temp = ipString.split(","); 
        const ipList = [...new Set(ipList_temp)];
        setPageNumber(1)
        setSubmittedIp(ipList);
    }

    //read in uploaded text file and parse into 1 string of ip addresses to query 
    const handleFileRead = async (e) => { 
        const content = fileReader.result; 
        var ipString = '';
        var lines = content.split('\n'); 
        for (var line=0;line<lines.length;line++){
            ipString+=lines[line]; 
            if (line !== lines.length-1) { 
                ipString+=','
            }
        }
        toast.success("TXT File uploaded.")
        handleBulkUpload(ipString); 

    }
    //handle upload of text file 
    const handleFile = (file) => { 
        try { 
            fileReader = new FileReader(); 
            fileReader.onloadend = handleFileRead;
            fileReader.readAsText(file);
        } catch(error) { 
            toast("File is of invalid TXT format.")
        }
    }
    //read in a JSON field. 
    const handleJSONUpload = (e) => {
        const fileReader = new FileReader();
        const listOfIP = []
        fileReader.readAsText(e.target.files[0], "UTF-8");
        e.target.value="";
        fileReader.onload = e => {
            try {
                let json = JSON.parse(e.target.result); 
                let a = Object.keys(json)[0];
                console.log(json[a]);

          
                for (const obj of json[a]) { 
                    console.log(obj)
                    if (!obj[jsonField]) { 
                        toast("JSON field doesn't exist in the JSON file.");
                        return null; 
                        }
                        listOfIP.push(obj[jsonField]) 
                    }
                } 
            catch (e) { 
                toast("Invalid JSON format.")
                return null;
            }   
          
        var ipString = listOfIP.join(","); 
        toast.success("JSON File uploaded.")
        handleBulkUpload(ipString);
        };
    }

    useEffect(()=>{
        setDidRender(true);
    },[]);


    useEffect( () => { 
        if (didRender) { 
            setPageNumber(1)
            setTotalPages(0)
            setip('')
            setSubmittedIp([])
            setResponse([])
            setjsonField('')
        }
    },[protocol])

    useEffect(()=>{
        if(didRender){
            handleSourceChange()
        }
    },[source]);

    useEffect(()=>{
        if(didRender){
            handlePaging()
        }
    },[pageNumber,pageSize,submittedIp]);

    return (
        <div className="Page">
            <Typography style={{"marginTop":"20px"}} variant="h4">Bulk Query</Typography>

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

            <div className="bulkQueryField">
            <TextField fullWidth onChange={e=>{setip(e.target.value)}}></TextField> 
            <Button endIcon={<SearchIcon/>} color="steelBlue" size="medium" variant="contained" onClick={e=>{handleBulkQuery()}}>Search</Button>
            </div>

            <div className="bulkUpload">
                <div className="txtUpload">
                    <Button endIcon={<ArticleIcon/>}  color="steelBlue" size="medium" variant="contained"  onClick={textButtonClick} > Upload TXT </Button>        
                    <input style={{display: 'none'}} ref={hiddenTextInput} id="uploadTXT" type="file" onChange={ (e)=>{handleFile(e.target.files[0]);e.target.value=""} }/>
                </div>
                
                <div className="jsonUpload">
                    {/* <TextField id="jsonField" label="JSON Field" onChange={e=>{setjsonField(e.target.value);}}></TextField>  */}
                    {/* <Button  endIcon={<DataObjectIcon/>} color="steelBlue" size="medium" variant="contained"  onClick={jsonButtonClick}> Upload JSON </Button>              */}
                    <Button endIcon={<DataObjectIcon/>}  color="steelBlue" size="medium" variant="contained"  onClick={togglePop} > Upload JSON </Button>             
                    <input style={{display: 'none'}} ref={hiddenJsonInput} id="uploadJSON" type="file" onChange={handleJSONUpload}/>
                </div>
                
            </div>
            <SourceView changeSource={setSource}></SourceView>
            {seen ? <PopUp submit={jsonButtonClick} setJsonField={setjsonField} toggle={togglePop} /> : null}
           
            <div className="result">
                { (totalPages === 0) 
                    ?  <div></div>

                    :  <div className="results">
                        <div className="pagination">          
                            <Pagination variant="outlined" color="primary" count={totalPages} page={pageNumber} onChange={handlePageChange}></Pagination>
                            <Typography>Showing page {pageNumber} of {totalPages}</Typography>
                            <SelectField width="120" size="small" defaultValue={pageSize}list={pageSizeList} name="Page Size" onChangeFunction={handleChange}></SelectField>

                        </div>
                        {/* <div className="resultTableContainer">
                        <ResultTable data={response}/>
                        </div> */}
                                    <div className="resultTableContainer">
                {
                    // source === sources[0]&& response.length>0  && <ResultTable data={response}/> 
                    source === sources[0]&& response.length>0  && <IP2LResult rowData={response}/> 

                }
                {
                    // source === sources[1] && response.length>0 && <GreynoiseResult data={response} /> 
                    source === sources[1] && response.length>0 && <GreyResult rowData={response} /> 


                }
            </div>
                        <div className="pagination"> 
                            <Typography>Showing page {pageNumber} of {totalPages}</Typography>  
                            <Pagination variant="outlined" color="primary" count={totalPages} page={pageNumber} onChange={handlePageChange}></Pagination>
                        </div>
                        </div>
                } 

            </div>


            <ToastContainer response={response}/>
        </div>
    )
}