import React from "react";
import { useNavigate } from "react-router-dom";
import MultiplePizzas from "../assets/mailchimp-YTZOyio3Udk-unsplash.jpg";
import "../styles/About.css";

function About() {
  

  

  return (
    <div className="about">
      <div
        className="aboutTop"
        style={{ backgroundImage: `url(${MultiplePizzas})` }}
      ></div>
      <div className="aboutBottom">
      <h1> ABOUT US</h1>
        
        <p>
          Welcome to our online banking portal, your reliable and secure destination for all your banking needs. We are committed to providing you with a seamless and convenient banking experience that empowers you to take control of your finances from the comfort of your home or on the go.
        </p>

        <h1> OUR MISSION</h1>
        <p>
          At our online banking portal, our mission is to redefine the way you bank by offering innovative and user-friendly digital banking solutions. We strive to be your financial partner, providing you with the tools and resources you need to achieve your financial goals and make informed decisions.
        </p>
       
      </div>
    </div>
  );
}

export default About;
