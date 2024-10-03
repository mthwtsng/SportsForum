// UserContext.js
import React, { createContext, useState } from 'react';

// Create a Context for user data
export const UserContext = createContext();

// Create a Provider component to wrap around parts of your app where you want to access user state
export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    return (
        <UserContext.Provider value={{ user, setUser }}>
            {children}
        </UserContext.Provider>
    );
};
