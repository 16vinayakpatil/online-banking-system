import { Navigate, useLocation } from "react-router";
import { useAuth } from "./auth";

export const RequireAuth = ({ children }) => {
  const auth = useAuth();
  const location = useLocation()

  if (!auth.jwt) {
    return <Navigate to="/start" state={{path: location.pathname}}/>;
  }

 

  return children;
};
