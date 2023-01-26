import Typography from '@mui/material/Typography';

export default function GreyNoise(props) { 
    const ip = props.ip;
    const result = props.result; 
    return (
        <div>
            <Typography>Greynoise Result</Typography>

            {result.length === 1 && <Typography>{result[0]}</Typography>}

            {result.length > 1 && <Typography>{ip} is from {result[1]} and it is considered {result[0]} </Typography>}
        
        </div>
    ) 
}