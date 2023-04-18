import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { useState } from 'react';
import { sources } from '../util/constants';
export default function SourceView(props) { 
    const [value, setValue] = useState(sources[0])
      
    const handleChange = (event, newValue) => {
        setValue(newValue)
        props.changeSource(newValue)
    };

    return (
        <Box  sx={{ width: '100%', bgcolor:'#E9FDFC' }}>
          <Tabs 
          value={value} 
          onChange={handleChange} 
          centered

          >
            <Tab  value={sources[0]} label={sources[0]} />
            <Tab value={sources[1]} label={sources[1]} />
          </Tabs>
        </Box>
      );
}