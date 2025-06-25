import React from "react";
import { Link } from 'react-router-dom';



const Card = (props) => {
  return (
    <div className="cards">
      <div className="card1">
        <img src={props.imgscr} alt="myPic" className="card__img" />
        <div className="card__info">
          <span className="card__category">{props.title}</span>
          <h3 className="card__title"><span className="s">{props.sname}</span></h3>
          <Link to="/adminlogin" className="btn btn-primary">Admin</Link>
          <Link to="/customerlogin" className="btn btn-primary">Customer</Link>
          <Link to="/employeelogin" className="btn btn-primary">Employee</Link>
        </div>

      </div>
    </div>
  );
}
export default Card;