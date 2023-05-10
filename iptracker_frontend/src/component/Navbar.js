import React, {useState, useEffect} from 'react';

import { useKeycloak } from "@react-keycloak/web";
import { Button } from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';

export default function Navbar() {
	const [toggleMenu, setToggleMenu] = useState(false);
	const [screenWidth, setScreenWidth] = useState(window.innerWidth);
	const { keycloak, initialized } = useKeycloak();

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
				<li>
					<a className="items" href="/FAQ">FAQ</a>
				</li>
				<li> 
				{!!keycloak.authenticated && (
                   <Button
				   startIcon = <LogoutIcon/>
					variant="outlined"
					color="navItem"
                     onClick={() => {
						keycloak.logout()
						window.accessToken = null
					 }
					 }
                   >
                     Logout - {keycloak.tokenParsed.preferred_username}
                   </Button>
                 )}
				</li>
			</ul>
			)}
			
			{/* <button onClick={toggleNav} className="btn">BTN</button> */}
		</nav>
	); }