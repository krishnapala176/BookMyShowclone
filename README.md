# 🎬 BookMyShow – Concept Map

This document outlines the key components and architecture of the **BookMyShow Clone** project based on Object, Context, and Information. It includes all major modules, user roles, data flows, and enhancements derived from real-world cinema platform design.

---

🔐 User Panels

### 1. Admin Panel

**Object:** Admin  
**Context:** Platform-level user responsible for managing theatres and movies.  
**Information:**
- Approve/reject theatre registrations
- View all users (customers, theatre owners)
- Add/edit/delete global movies
- Monitor overall bookings and payments
- Lock/unlock theatre accounts
- View all dashboards and analytics

---

### 2. Theatre Panel

**Object:** Theatre  
**Context:** Venue owner who schedules shows for movies.  
**Information:**
- Register as a theatre (requires admin approval)
- Add/edit/delete shows linked to approved movies
- Assign screens and set seat availability
- Upload movie posters and trailers
- View bookings and payment status for their shows
- Edit profile, manage theatre info
- View theatre-specific dashboard

---

### 3. Customer Panel

**Object:** Customer  
**Context:** General user who books tickets for shows.  
**Information:**
- Register/login (JWT secured)
- Browse movie listings with show timings
- Filter by genre, language, location
- Book available shows (select seat count)
- Make payments via selected method
- View booking history and status
- Cancel bookings (based on policy)
- Receive email confirmations and receipts

---

🗃️ Core Models & Schemas

### **Object:** User  
**Context:** Central identity system  
**Key Fields:**  
- name, email, password, role (`CUSTOMER`, `THEATRE`, `ADMIN`)  
- verified (boolean), createdAt  

### **Object:** Movie  
**Context:** Movie catalog visible to users  
**Key Fields:**  
- title, description, genre, language  
- posterUrl, trailerUrl  
- createdBy (admin)  
- createdAt, updatedAt  

### **Object:** Theatre  
**Context:** Cinema venue that runs movie shows  
**Key Fields:**  
- name, location, screenList[]  
- approved (boolean), createdBy (userId)  
- createdAt  

### **Object:** Show  
**Context:** Represents a scheduled movie show  
**Key Fields:**  
- movieId, theatreId  
- screenNo, timing, totalSeats, availableSeats  
- createdAt, updatedAt  

### **Object:** Booking  
**Context:** Ticket booking made by a customer  
**Key Fields:**  
- userId, showId  
- selectedSeats, bookingStatus (`CONFIRMED`, `CANCELLED`)  
- createdAt  

### **Object:** Payment  
**Context:** Payment made during booking  
**Key Fields:**  
- bookingId, method (`CARD`, `UPI`, etc.)  
- paymentStatus (`SUCCESS`, `FAILED`)  
- amount, timestamp  

---

📧 Email & Notifications

**Object:** EmailService  
**Context:** System email handler  
**Information:**
- Sends registration confirmation  
- Booking confirmation emails  
- Payment receipt or failure emails  
- Booking cancellation notification  
- Password reset OTP (planned)  

---

📁 File Uploads

**Object:** FileUploader  
**Context:** Multimedia upload system  
**Information:**
- Upload and store movie posters  
- Upload trailer video links (YouTube, mp4, etc.)  
- Store file paths in movie objects  
- Files stored locally or on cloud (optionally)  

---

🔒 Authentication & Security

**Object:** JwtService  
**Context:** Handles JWT creation and validation  
**Information:**
- Create token on login/registration  
- Decode token to extract email/role  
- Secures all protected APIs based on role  

**Object:** AuthenticationFilter  
**Context:** Spring filter that validates tokens  
**Information:**
- Intercepts requests, sets user context  
- Handles role-based endpoint access  

---

📊 Dashboards & Reports

**Object:** DashboardController  
**Context:** Shows contextual data to each user  
**Information:**
- Admin: count of users, theatres, bookings, payments  
- Theatre: bookings by show, revenue breakdown  
- Customer: booking history, active shows  

---

🚫 Constraints and Limitations

- Theatre must be approved by admin before adding shows  
- Shows can't be booked once fully occupied  
- Customers can't cancel bookings after showtime  
- Payments are mocked (no real payment gateway yet)  
- Only approved movies can be scheduled in shows  

---

🛠️ Future Enhancements

- Real-time seat selection (via sockets)  
- SMS notifications  
- Integration with Razorpay or Stripe  
- Expired token handling and refresh tokens  
- OTP-based password recovery  
- Rating & reviews for movies  
- Seat mapping & seat-level availability  
- Movie trailers autoplay preview  
- Cross-region show listings  

---

⚙️ Tech Stack Overview (for Dev Reference)

**Backend:** Java, Spring Boot  
**Database:** MongoDB  
**Security:** Spring Security + JWT  
**Email:** JavaMailSender  
**File Uploads:** MultipartFile, local storage  
**Testing:** Postman, Swagger (optional)  
**Frontend (Optional):** React.js (future scope)  
**Deployment (Optional):** Docker / AWS EC2 / Railway  

---

✅ Conclusion

This concept map helps contributors and stakeholders understand how the BookMyShow application is structured and how each module connects functionally and contextually. Use this as the base for further development and integration of real-world cinema features.
