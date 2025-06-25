import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [jwt, setJwt] = useState("");

  const login = (username, password, jwt) => {
    setUsername(username);
    setPassword(password);
    setJwt(jwt);
  };

 
  return (
    <AuthContext.Provider value={{ jwt, username, password, login }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
