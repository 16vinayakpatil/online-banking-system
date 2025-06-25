import "./App.css";
import { AuthProvider } from "./utility/auth";
import { RequireAuth } from "./utility/RequireAuth";
import { Route, Routes } from "react-router-dom";
import Footer from "./Components/Footer";
import Main from "./Main";
// import Home2 from "./Home2";
import Home from "./Home";
import Menu from "./pages/Service";
import About from "./pages/About";
// import About1 from "./pages/About1";
import Contact from "./pages/Contact";
import Products from "./pages/Products";
import AdminLogin from "./Components/AdminLogin";
// import AdminLogin1 from "./Components1/AdminLogin1";

// import VendorLogin from "./Components1/VendorLogin";
import CustomerLogin from "./Components/CustomerLogin";
import EmployeeLogin from "./Components/EmployeeLogin";
import Start from "./Components/Start";
// import Start1 from "./Components1/Start1";
import AdminDashboard from "./Components/AdminDashboard";
// import AdminDashboards from "./Components1/AdminDashboards";
// import TLDashboard from "./Components1/TLDashboard";
// import VendorDashboard from "./Components1/VendorDashboard";
import EmployeeDashboard from "./Components/EmployeeDashboard";
import CustomerDashboard from "./Components/CustomerDashboard";
import Adminprofile from "./Components/Adminprofile";
import CustomerRegister from "./Components/CustomerRegister";
import EmployeeList from "./Components/EmployeeList";
import CustomerList from "./Components/CustomerList";
// import AddDeleteUsers from "./Components1/AddDeleteUsers";
// import AddUsers from "./Components1/AddUsers";
// import RetriveReports from "./Components1/RetriveReports";
import Deposit from "./Components/Deposit";
import Withdraw from "./Components/Withdraw";
import BankTransfer from "./Components/BankTransfer";
import ApplyLoan from "./Components/ApplyLoan";
import AllLoans from "./Components/AllLoans";
import LoanPayment from "./Components/LoanPayment";
import BuyGiftcard from "./Components/BuyGiftcard";
import AllGiftCards from "./Components/AllGiftCards";
import MyTransaction from "./Components/MyTransaction";
import EmployeeTransaction from "./Components/EmployeeTransaction";
import PendingLoans from "./Components/PendingLoans";
import ActivateLoans from "./Components/ActivateLoans"
import CloseLoans from "./Components/CloseLoans";
import AllLoanEmployee from "./Components/AllLoanEmployee";
import MyLocker from "./Components/MyLocker";
import ApplyLocker from "./Components/ApplyLocker";
import LockerPayment from "./Components/LockerPayment";
import ClosingRequest from "./Components/ClosingRequest";
import AllLockers from "./Components/AllLockers";
import ActivateLocker from "./Components/ActivateLocker";
import CloseLocker from "./Components/CloseLocker";
import ClosingRequestTable from "./Components/ClosingRequestTable";
import PendingRequest from "./Components/PendingRequest";
import MyCreditCards from "./Components/MyCreditCards";
import ApplyCreditCard from "./Components/ApplyCreditCard";
import CloseCreditCard from "./Components/CloseCreditCard";
import AllCreditCards from "./Components/AllCreditCards";
import ActivateCreditCard from "./Components/ActivateCreditCard";
import PendingRequestCreditCard from "./Components/PendingRequestCreditCard";
import ClosingRequestCreditCard from "./Components/ClosingRequestCreditCard";
import EmployeeCloseCreditCard from "./Components/EmployeeCloseCreditCard";
import CustomerAccount from "./Components/CustomerAccount";
import ActivateAccount from "./Components/ActivateAccount";
import PendingAccounts from "./Components/PendingAccounts";
import AllAccounts from "./Components/AllAccounts";
import CustomerProfile from "./Components/CustomerProfile";
import EmployeeRegister from "./Components/EmployeeRegister";
import EmployeeProfile  from "./Components/EmployeeProfile";
// import PendingUsers from "./Components1/PendingUsers";
// import ApprovedUsers from "./Components1/ApprovedUsers";
// import RejectedUsers from "./Components1/RejectedUsers";
// import MarkProductivity from "./Components1/MarkProductivity";
// import ViewAddedReports from "./Components1/ViewAddedReports";
// import TLLogin from "./Components1/TLLogin";
// import DeleteUsers from "./Components1/DeleteUsers";
// import VendorProfile from "./Components1/VendorProfile";
// import TlProfile from "./Components1/TlProfile";
// import ForgotPassword from "./Components1/ForgotPassword";
// import ResetPassword from "./Components1/ResetPassword";
import EmployeeForgotPassword from "./Components/EmployeeForgotPassword";
import EmployeeResetPassword from "./Components/EmployeeResetPassword";
import CustomerForgotPassword from "./Components/CustomerForgotPassword";
import CustomerResetPassword from "./Components/CustomerResetPassword";
import MakePayment from "./Components/MakePayment";
import PayEmi from "./Components/PayEmi";
import EmiCalculator from "./Components/EmiCalculator";




function App() {
  return (
    <div>
      <AuthProvider>
      <Routes>
      {/* <Route path="/" element={<Main />} /> */}
        <Route path="/" element={<Home />} />
        {/* <Route path="/home2" element={<Home2 />} /> */}
        <Route path="/about" element={<About />} />
        {/* <Route path="/about1" element={<About1 />} /> */}
        <Route path="/service" element={<Menu />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/footer" element={<Footer />} />
        <Route path="/customerregister" element={<CustomerRegister />} />
        <Route path="/start" element={<Start />} />
        {/* <Route path="/start1" element={<Start1 />} /> */}
        <Route path="/product" element={<Products />} />
        <Route path="/adminlogin" element={<AdminLogin />} />
        <Route path="/customerlogin" element={<CustomerLogin />} />
        <Route path="/employeelogin" element={<EmployeeLogin />} />
        {/* <Route path="/adminlogin1" element={<AdminLogin1 />} />
        <Route path="/tllogin" element={<TLLogin />} />
        <Route path="/vendorlogin" element={<VendorLogin />} /> */}
        <Route path="/employee-forgot-password" element={<EmployeeForgotPassword />} />
          <Route path="/employee-reset-password" element={<EmployeeResetPassword />} />
          <Route path="/customer-forgot-password" element={<CustomerForgotPassword />} />
          <Route path="/customer-reset-password" element={<CustomerResetPassword />} />

        <Route path="/admin/*" element={<RequireAuth><AdminDashboard/></RequireAuth>} >
          <Route path="employeelist" element={<EmployeeList />} />
          <Route path="create" element={<EmployeeRegister />} />
          <Route path="profile" element={<Adminprofile />} />
        </Route>

        {/* <Route path="/admin1/*" element={<RequireAuth><AdminDashboards/></RequireAuth>} >
           <Route path="add-delete" element={<AddDeleteUsers />} />
          <Route path="addusers" element={<AddUsers />} />
          <Route path="deleteusers" element={<DeleteUsers />} />
          <Route path="retrivereports" element={<RetriveReports />} />
          <Route path="profile" element={<Adminprofile />} />
        </Route>

        <Route path="/tl/*" element={<RequireAuth><TLDashboard/></RequireAuth>} >
           <Route path="pending" element={<PendingUsers />} />
           <Route path="profile" element={<TlProfile />} />
           <Route path="approved" element={<ApprovedUsers />} />
          <Route path="rejected" element={<RejectedUsers />} />
          
        </Route>

        <Route path="/vendor/*" element={<RequireAuth><VendorDashboard/></RequireAuth>} >
           <Route path="mark" element={<MarkProductivity />} />
           <Route path="profile" element={<VendorProfile />} />
          <Route path="view" element={<ViewAddedReports />} />
          <Route path="profile" element={<Adminprofile />} />
        </Route> */}

        <Route path="/employee/*" element={<RequireAuth><EmployeeDashboard/></RequireAuth>} >
          <Route path="customerlist" element={<CustomerList />} />
          <Route path="employeetransaction" element={<EmployeeTransaction />} />
          <Route path="employeepending" element={<PendingLoans />} />
          <Route path="activateloan" element={<ActivateLoans />} />
          <Route path="closeloan" element={<CloseLoans />} />
          <Route path="allloan" element={<AllLoanEmployee />} />
          <Route path="alllockers" element={<AllLockers />} />
          <Route path="activatelocker" element={<ActivateLocker />} />
          <Route path="closelocker" element={<CloseLocker />} />
          <Route path="closingrequest" element={<ClosingRequestTable />} />
          <Route path="pendingrequest" element={<PendingRequest />} />
          <Route path="allcreditcards" element={<AllCreditCards />} />
          <Route path="activatecreditcard" element={<ActivateCreditCard />} />
          <Route path="pendingcreditcardrequest" element={<PendingRequestCreditCard />} />
          <Route path="activateaccount" element={<ActivateAccount />} />
          <Route path="allaccounts" element={<AllAccounts />} />
          <Route path="pendingaccounts" element={<PendingAccounts />} />
          <Route path="closingcreditcardrequest" element={<ClosingRequestCreditCard />} />
          <Route path="closecreditcard" element={<EmployeeCloseCreditCard />} />
          <Route path="employeeprofile" element={<EmployeeProfile/>} />
        </Route>

        <Route path="/customer/*" element={<RequireAuth><CustomerDashboard/></RequireAuth>}>
        <Route path="customerprofile" element={<CustomerProfile />} />
          <Route path="customerdeposit" element={<Deposit />} />
          <Route path="customertransaction" element={<MyTransaction />} />
          <Route path="customerwithdraw" element={<Withdraw />} />
          <Route path="customertransfer" element={<BankTransfer />} />
          <Route path="customerapplyloan" element={<ApplyLoan />} />
          <Route path="customerallloans" element={<AllLoans />} />
          <Route path="customerloanpayment" element={<LoanPayment />} />
          <Route path="customerbuygiftcard" element={<BuyGiftcard />} />
          <Route path="customerallgiftcard" element={<AllGiftCards />} />
          <Route path="customermylocker" element={<MyLocker />} />
          <Route path="customerapplylocker" element={<ApplyLocker />} />
          <Route path="customerlockerpayment" element={<LockerPayment />} />
          <Route path="customerclosinglocker" element={<ClosingRequest />} />
          <Route path="customerallcreditcards" element={<MyCreditCards />} />
          <Route path="customerapplycreditcard" element={<ApplyCreditCard />} />
          <Route path="customerclosecreditcard" element={<CloseCreditCard />} />
          <Route path="customermakepayment" element={<MakePayment />} />
          <Route path="customerpayemi" element={<PayEmi />} />
          <Route path="customeremicalculator" element={<EmiCalculator />} />
          <Route path="customeraccount" element={<CustomerAccount />} />
        </Route>
      </Routes>
      </AuthProvider>
    </div>
    
  );
}

export default App;
