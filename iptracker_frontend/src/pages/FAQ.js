import { Typography } from "@mui/material"
import { useState, useEffect } from "react"
export default function FAQ() { 
    const [lastUpdated, setlastUpdated] = useState([])

    const getUpdated = async () => { 
        const response = await fetch("/getAllUpdates"); 
        const json = await response.json();
        console.log(json.response.content);
        return json.response.content;
    }
    useEffect(() => {
        getUpdated().then(
            result => setlastUpdated(result));
    },[]);
    
    return( 
        <div id="Page" className="Page">
            <Typography style={{"marginTop":"20px"}} variant="h4"> Frequently Asked Questions </Typography>
            <Typography variant="h5"> When was this last updated?</Typography>
            {lastUpdated.length > 0 && 
                lastUpdated.map( (item,i) => 
                <div key={i}>
                    {item.document} | {item.lastUpdated}
                </div>
            )

            }

        </div>
    )
}

