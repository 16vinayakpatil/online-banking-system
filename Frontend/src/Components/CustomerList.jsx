import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import config from './Configuration/config';

const ListCustomerComponent = () => {
  const [customers, setCustomers] = useState([]);
  const [filterUsername, setFilterUsername] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('jwt');

    axios
      .get(`http://localhost:${config.port}/login/employee/all-customers`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      .then((response) => {
        const updatedCustomers = response.data.map((customer) => ({
          ...customer,
          accNo: '',
          message: '',
        }));
        setCustomers(updatedCustomers);
        console.log(response);
      })
      .catch((error) => {
        console.error('Failed to retrieve customers:', error);
      });
  }, []);

  const handleAccNoChange = (customerId, event) => {
    const updatedCustomers = customers.map((customer) => {
      if (customer.id === customerId) {
        return { ...customer, accNo: event.target.value };
      }
      return customer;
    });

    setCustomers(updatedCustomers);
  };

  const handleDeleteCustomer = async (customerId) => {
    const token = localStorage.getItem('jwt');
    const customer = customers.find((customer) => customer.id === customerId);
    const accNo = customer.accNo;

    try {
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      };

      const requestData = {
        accno: accNo,
      };

      await axios.delete('http://localhost:8081/employee/delete-customer', { data: requestData, ...config });

      // Remove the deleted customer from the state
      const updatedCustomers = customers.filter((customer) => customer.id !== customerId);
      setCustomers(updatedCustomers);
    } catch (error) {
      console.error('Error occurred while deleting the customer:', error);
      const updatedCustomers = customers.map((customer) => {
        if (customer.id === customerId) {
          return { ...customer, message: 'Error occurred while deleting the customer.' };
        }
        return customer;
      });

      setCustomers(updatedCustomers);
    }
  };

  return (
    <div className="container">
      <h2 className="text-center">Customers Details</h2>
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
            {customers
              .filter((customer) =>
                customer.username.toLowerCase().includes(filterUsername.toLowerCase())
              )
              .map((customer) => (
                <tr key={customer.id}>
                  <td>{customer.username}</td>
                  <td>{customer.password}</td>
                  <td>{customer.email}</td>
                  <td>{customer.address}</td>
                  <td>{customer.phone}</td>
                  <td>
                    <input
                      type="text"
                      value={customer.accNo}
                      onChange={(event) => handleAccNoChange(customer.id, event)}
                      placeholder="Account Number"
                    />
                    <button
                      className="btn btn-danger"
                      onClick={() => handleDeleteCustomer(customer.id)}
                      style={{ marginLeft: '10px' }}
                    >
                      Delete
                    </button>
                    <p>{customer.message}</p>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListCustomerComponent;
