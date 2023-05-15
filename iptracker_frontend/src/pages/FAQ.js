import { Typography } from "@mui/material"
import { useState, useEffect } from "react"
import Link from '@mui/material/Link';

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
            <Typography variant="h5" sx={{textDecoration:'underline'  }}> When was this last updated?</Typography>
            {lastUpdated.length > 0 && 
                lastUpdated.map( (item,i) => 
                <div key={i}>
                    {item.document} | {item.lastUpdated}
                </div>
            )
            }

        
        <Typography variant="h5" sx={{textDecoration:'underline'  }}>What are the valid IP addresses accepted?</Typography>
        <Typography>We accept both IPV4 and IPv6 addresses. We use the library below to check the validity. </Typography><Link href="https://guava.dev/releases/19.0/api/docs/com/google/common/net/InetAddresses.html">Google InetAddresses</Link>
        <Typography variant="h5" sx={{textDecoration:'underline'  }}>What are the proper formats required for Bulk Query?</Typography>
        <Typography>1. For field input, simply have multiple IP addresses of the same type separated by a comma with no spaces.</Typography>
        <Typography>2. For TXT File Upload and JSON File Upload, refer to images below. </Typography>
        <div className="faqImages">
            <img src={"/BulkTXT.png"} style={{ width: 500, height: 400 }}  alt="TXT" />
            <img src={"/BulkJSON.png"} style={{ width: 500, height: 400 }}  alt="JSON" />
        </div>

        </div>
    )
}

