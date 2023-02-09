import { Typography } from "@mui/material";
import React from "react"
//help display result from single and bulk queries for each IP address 
//acepts two props - ip address and the results for that ip address 
const Result= (props) =>  { 
    const data = props.data; 

    function checkNull(data){ 
        if (data.country_name === null && data.country_code === null && data.region_name === null) { 
            return true;
        }
        else return false;
    }
    return( 
        <div>
            { checkNull(data) && <Typography> {data.ip} cannot be found!</Typography>} 
            { !checkNull(data) && <Typography>{data.ip} is from {data.country_name}</Typography>}
        </div>
    )
}

export default React.memo(Result)