import Typography from '@mui/material/Typography';
import { useState } from 'react';
import SingleQuery from '../component/SingleQuery';
import { Button } from '@mui/material'; 
import BulkQuery from '../component/BulkQuery';
import ReverseLookup from '../component/ReverseLookup';

export default function Query() { 
    //determine which feature is being shown on app 
    const [feature,setFeature] = useState(0); 

    return (
        <div>
            <div>
                <Button variant="contained" onClick={e=>{setFeature(1)}}>Single Query</Button> 
                <Button variant="contained" onClick={e=>{setFeature(2)}}>Bulk Queries</Button> 
                <Button variant="contained" onClick={e=>{setFeature(3)}}>Reverse Lookup</Button> 
            </div>
            {feature === 1 && <SingleQuery/>}
            {feature === 2 && <BulkQuery/>}
            {feature === 3 && <ReverseLookup/>}
        </div>

    ) 

}