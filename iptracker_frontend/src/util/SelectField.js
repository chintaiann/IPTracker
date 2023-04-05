import {MenuItem,FormControl,Select,InputLabel, OutlinedInput}from "@mui/material";
import { useState , useEffect} from "react";


export default function SelectField(props) { 
const list = props.list;
const name = props.name; 
const [value,setValue] = useState(""); //value is the field being shown 
const width = parseInt(props.width)


const handle = (event) => { 
    props.onChangeFunction(name,event.target.value); //pass in onChangeFunction to change parent state 
    setValue(event.target.value);
}

useEffect( () => { 
    setValue('');
},[props.protocol]) 

    return ( 
        <FormControl size={props.size}sx={{ m: 1, width:width?width:300}}>
        <InputLabel id="demo-multiple-name-label">{name}</InputLabel>
        <Select
        value={value}
        label={value}
        onChange= {handle}
        input={<OutlinedInput label={name} />}
        >
        {list.map((item) => (
            <MenuItem
            key={item.value}
            value={item.value}
            id="filterField"
            >
            {item.label}
            </MenuItem>
        ))}
        </Select>
    </FormControl>    )
} 