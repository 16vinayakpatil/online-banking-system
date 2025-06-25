import React, { useState } from 'react';
import axios from 'axios';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import config from './Configuration/config';

function App() {
  const [isOpen, setIsOpen] = useState(false);
  const [userDetails, setUserDetails] = useState(null);

  const handleOpen = async () => {
    try {
      const token = localStorage.getItem('jwt'); // Assuming you store the token in local storage
      console.log(token);
      const response = await axios.get(`http://localhost:${config.port}/login/customer/profile`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUserDetails(response.data);
      setIsOpen(true);
    } catch (error) {
      console.error('Error retrieving profile details:', error);
    }
  };

  const handleClose = () => {
    setIsOpen(false);
    setUserDetails(null);
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '10vh' }}>
      <Button variant="contained" color="primary" onClick={handleOpen}>
        View Profile
      </Button>

      <Dialog open={isOpen} onClose={handleClose}>
        <DialogTitle>Profile Details</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {userDetails ? (
              userDetails.split('\n').map((line, index) => (
                <React.Fragment key={index}>
                  {line}
                  <br />
                </React.Fragment>
              ))
            ) : (
              'Loading...'
            )}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Close</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default App;
