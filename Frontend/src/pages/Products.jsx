import React from 'react'
import Card from '../Cards';
import Sdata from '../Sdata';
import '../index.css';
import { useNavigate } from "react-router-dom";

function Products() {
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate("/");
  };
  return (
    <div>
    <button className="backButton" onClick={handleBackClick}>
          Back to Home
        </button>
    <h1 className="heading_style">Our Products </h1>
    {Sdata.map((val) => (
      <Card
        key={val.title} // Add a unique key prop for each card
        imgscr={val.imgscr}
        sname={val.sname}
        title={val.title}
        links={val.links}
      />
    ))}
    
  </div>
  )
}

export default Products
