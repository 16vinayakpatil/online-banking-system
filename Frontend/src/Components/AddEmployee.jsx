import React, { useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './AddEmployee.css';
import config from './Configuration/config';

function AddEmployee() {
  const [data, setData] = useState({
    username: '',
    email: '',
    password: '',
    address: '',
    phone: ''
    // image: ''
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    const formdata = new FormData();
    formdata.append('username', data.username);
    formdata.append('email', data.email);
    formdata.append('password', data.password);
    formdata.append('address', data.address);
    formdata.append('phone', data.phone);
    // formdata.append("image", data.image);
    const token = localStorage.getItem('jwt'); // Get the JWT token from localStorage
    axios
      .post(`http://localhost:${config.port}/login/admin/register-employee`, formdata, {
        headers: {
          Authorization: `Bearer ${token}` // Pass the token in the Authorization header
        }
      })
      .then((res) => {
        toast.success('Employee created successfully');
        // Clear form data
        setData({
          username: '',
          email: '',
          password: '',
          address: '',
          phone: ''
        });
      })
      .catch((err) => {
        toast.error('Failed to create employee. Please try again.');
        console.log(err);
      });
  };

  return (
    <div className="add-employee-container">
      <ToastContainer />

      <form className="employee-form" onSubmit={handleSubmit}>
        <h2>Add Employee</h2>
        <div className="form-group">
          <label htmlFor="inputName">Username</label>
          <input
            type="text"
            className="form-control"
            id="inputName"
            placeholder="Enter Username"
            autoComplete="off"
            value={data.username}
            onChange={(e) => setData({ ...data, username: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label htmlFor="inputEmail4">Email</label>
          <input
            type="email"
            className="form-control"
            id="inputEmail4"
            placeholder="Enter Email"
            autoComplete="off"
            value={data.email}
            onChange={(e) => setData({ ...data, email: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label htmlFor="inputPassword4">Password</label>
          <input
            type="password"
            className="form-control"
            id="inputPassword4"
            placeholder="Enter Password"
            value={data.password}
            onChange={(e) => setData({ ...data, password: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label htmlFor="inputSalary">Phone</label>
          <input
            type="text"
            className="form-control"
            id="inputSalary"
            placeholder="Enter Phone"
            autoComplete="off"
            value={data.phone}
            onChange={(e) => setData({ ...data, phone: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label htmlFor="inputAddress">Address</label>
          <input
            type="text"
            className="form-control"
            id="inputAddress"
            placeholder="1234 Main St"
            autoComplete="off"
            value={data.address}
            onChange={(e) => setData({ ...data, address: e.target.value })}
          />
        </div>
        {/* <div class="col-12 mb-3">
          <label class="form-label" for="inputGroupFile01">
            Select Image
          </label>
          <input
            type="file"
            class="form-control"
            id="inputGroupFile01"
            onChange={(e) => setData({ ...data, image: e.target.files[0] })}
          />
        </div> */}
        <div className="form-group">
          <button type="submit" className="btn btn-primary">
            Create
          </button>
        </div>
      </form>
    </div>
  );
}

export default AddEmployee;
