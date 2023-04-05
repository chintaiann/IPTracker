import React from "react";
import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
export default function PopUp(props) {

    const handleClick = (event) => {
        props.toggle();
    };

    const handleSubmit = (event) => { 
        props.submit();
        props.toggle();
    }

    const handle = (event) => { 
        props.setJsonField(event.target.value);
    }


  return (
    <div className="modal">
    <div className="modal_content">
      <span className="close" onClick={handleClick}>
        &times;
      </span>
      <div className="modal_content2">
        <TextField label="JSON Field" onChange={handle}></TextField>
        <Button endIcon={<NavigateNextIcon/>} color="steelBlue" size="medium" variant="contained" onClick={handleSubmit}>Next</Button> 
      </div>
    </div>
  </div>
  );
 }
