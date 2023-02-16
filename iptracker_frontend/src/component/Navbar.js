import React, {useState, useEffect} from 'react';
import { TextField, MenuItem } from '@mui/material';
import { protocols } from '../util/constants';

export default function Navbar() {
	const [toggleMenu, setToggleMenu] = useState(false);
	const [screenWidth, setScreenWidth] = useState(window.innerWidth);

	const toggleNav = () => {
		setToggleMenu(!toggleMenu);
	};
	useEffect(() => {
			const changeWidth = () => {
			setScreenWidth(window.innerWidth);
		};

		window.addEventListener('resize', changeWidth);

		return () => {
			window.removeEventListener('resize', changeWidth);
		};

	}, []);

	return (
		<nav>
			{(toggleMenu || screenWidth > 500) && (
			<ul className="nav_link">
				<li>
					<a className="items" href="/Single">Single Query</a>
				</li>
				<li>
					<a className="items" href="/Bulk">Bulk Query</a>
				</li>
                <li>
					<a className="items" href="/Reverse">Reverse Lookup</a>
				</li>

				{/* <li>
					{(username !== '')
					?  <a className='items' onClick={() => {handleLogout();}}>Logout</a>
					:  <a className="items" href="/login">Login/Register</a>}
				</li> */}
			</ul>
			)}

			{/* <button onClick={toggleNav} className="btn">BTN</button> */}
		</nav>
	); }