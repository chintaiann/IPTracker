import {isEmpty} from "lodash"
import { Typography } from "@mui/material";
//acepts two props - ip address and the results for that ip address 
export default function Result(props) { 
    const data = props.data; 
    const ip = props.ip;
    return( 
        <div>
            {data.status !== "OK" &&  <Typography>{ip} : {data.status}</Typography>}
            {data.status === "OK" && <Typography>{ip} is from {data.countryLong}</Typography>}
        </div>
    )
}