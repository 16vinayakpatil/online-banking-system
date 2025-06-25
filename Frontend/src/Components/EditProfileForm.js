import React, { useState } from 'react';
import axios from 'axios';

const EditProfileForm = ({ profileData, setIsEditing }) => {
  const [formData, setFormData] = useState(profileData);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Submit the updated profile data to the backend
    axios.put(`/api/edit/${formData.id}`, formData)
      .then(response => {
        // Update the profile data in the ProfilePage component
        setIsEditing(false);
      })
      .catch(error => {
        console.error('Error updating profile:', error);
      });
  };

  return (
    <div className="edit-profile">
      <h2>Edit Profile</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
        </label>
        <label>
          Email:
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </label>
        <label>
          Address:
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
        </label>
        <label>
          Phone:
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Save Changes</button>
        <button type="button" onClick={() => setIsEditing(false)}>Cancel</button>
      </form>
    </div>
  );
};

export default EditProfileForm;
