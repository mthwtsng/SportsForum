// Homepage.js
import React, { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './css/pages.css'; // Import the CSS file
import {UserContext} from './UserContext';

const Homepage = () => {
    const navigate = useNavigate();
    const {user, setUser} = useContext(UserContext);


    const handleNavigation = (path) => {
        navigate(`/${path}`);
    };

    useEffect(() => {
        // Check if user is logged in by fetching user-info from the server
        const checkUser = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/user-info', {
                    method: 'GET',
                    credentials: 'include',
                });

                if (response.ok) {
                    const data = await response.json();
                    setUser({ username: data.username });
                }
            } catch (error) {
                console.error("Failed to get user info", error);
            }
        };

        checkUser();
    }, [setUser]);

    return (
        <div id="app">
            <header className="navbar">
                <div className="navbar-left">
                    <button onClick={() => handleNavigation('home')}>Home</button>
                    <button onClick={() => handleNavigation('services')}>Services</button>
                    <button onClick={() => handleNavigation('about')}>About</button>
                    <button onClick={() => handleNavigation('contact')}>Contact</button>
                </div>
                <div className="navbar-right">
                    {user ? (
                        <span>Welcome, {user.username}</span>
                    ) : (
                        <>
                            <button onClick={() => handleNavigation('login')}>Login</button>
                            <button onClick={() => handleNavigation('signup')}>Signup</button>
                        </>
                    )}
                </div>
            </header>

            <main>
                <h1>Welcome to the Home Page!</h1>
            </main>
        </div>
    );
};

export default Homepage;
