import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import config from './Configuration/config';

const ListEmployeeComponent = () => {
  const [employees, setEmployees] = useState([]);
  const [filterUsername, setFilterUsername] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('jwt');

    axios
      .get('http://localhost:9999/login/admin/all-employees', {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      .then((response) => {
        setEmployees(response.data);
        console.log(response);
      })
      .catch((error) => {
        console.error('Failed to retrieve employees:', error);
      });
  }, []);

  const deleteEmployee = (userId) => {
    const token = localStorage.getItem('jwt');

    axios
      .delete(`http://localhost:${config.port}/login/admin/delete-employee/${userId}`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        console.log(res);
        // Remove the deleted employee from the state
        setEmployees(employees.filter((employee) => employee.id !== userId));
      })
      .catch((error) => {
        console.error(error);
        // Handle errors
      });
  };

  return (
    <div className="container">
      <h2 className="text-center" style={{ color: 'white' }}>Employees Details</h2>
      <Link to="/admin/create" className="btn btn-primary mb-2">
        Add Employee
      </Link>
      <div>
        <div className="form-group">
          <label htmlFor="usernameFilter"></label>
          <input
            type="text"
            id="usernameFilter"
            className="form-control"
            style={{
              maxWidth: '300px',
              backgroundColor: 'rgba(255, 255, 255, 0.7)',
            }}
            value={filterUsername}
            onChange={(e) => setFilterUsername(e.target.value)}
            placeholder="Enter username to filter"
          />
        </div>
      </div>
      <div style={{ overflowY: 'scroll', maxHeight: '400px' }}>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Username</th>
              <th>Password</th>
              <th>Email</th>
              <th>Address</th>
              <th>Phone</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees
              .filter((employee) =>
                employee.username.toLowerCase().includes(filterUsername.toLowerCase())
              )
              .map((employee) => (
                <tr key={employee.id}>
                  <td>{employee.username}</td>
                  <td>{employee.password}</td>
                  <td>{employee.email}</td>
                  <td>{employee.address}</td>
                  <td>{employee.phone}</td>
                  <td>
                    <button
                      className="btn btn-danger"
                      onClick={() => deleteEmployee(employee.id)}
                      style={{ marginLeft: '10px' }}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListEmployeeComponent;
