// import React from "react";
// import PizzaLeft from "../assets/melinda-gimpel-5Ne6mMQtIdo-unsplash.jpg";
// import "../styles/Contact.css";

// function Contact() {
//   const handleSubmit = (event) => {
//     event.preventDefault();
//     // Perform any necessary form validation or data processing here

//     // Show alert after submitting the form
//     alert("Message sent successfully!");

//     // Reset the form fields
//     event.target.reset();
//   };

//   return (
//     <div className="contact">
//       <div
//         className="leftSide"
//         style={{ backgroundImage: `url(${PizzaLeft})` }}
//       ></div>
//       <div className="rightSide">
//         <h1> Contact Us</h1>

//         <form id="contact-form" onSubmit={handleSubmit}>
//           <label htmlFor="name">Full Name</label>
//           <input name="name" placeholder="Enter full name..." type="text" />
//           <label htmlFor="email">Email</label>
//           <input name="email" placeholder="Enter email..." type="email" />
//           <label htmlFor="message">Message</label>
//           <textarea
//             rows="6"
//             placeholder="Enter message..."
//             name="message"
//             required
//           ></textarea>
//           <button type="submit"> Send Message</button>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default Contact;
