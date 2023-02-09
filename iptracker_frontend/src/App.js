import './App.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SingleQuery from './component/SingleQuery';
import BulkQuery from './component/BulkQuery';
import ReverseLookup from './component/ReverseLookup';

import Navbar from './component/Navbar';
function App() {
  return (
    <div className="home">
      <header>
        <Navbar/>
      </header>
      <Router>
        <Routes>
          <Route path='/' element={<SingleQuery/>}/>
          <Route path='/Single' element={<SingleQuery/>} />
          <Route path='/Bulk' element={<BulkQuery/>} />
          <Route path='/Reverse' element={<ReverseLookup/>} />
        </Routes>
      </Router>    
    </div>
  );
}

export default App;
