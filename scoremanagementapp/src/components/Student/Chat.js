
import React, { useState, useEffect, useRef, useCallback } from "react";
import { io } from "socket.io-client";


const Chat = ({ classDetailId = "1" }) => { 
  const socketRef = useRef(null);

  
  useEffect(() => {
    if (!socketRef.current) {
      console.log("Initializing socket connection...");
      const token = Cookies.get("token");
      socketRef.current = io(`http://localhost:8099?token=${token}`);

      socketRef.current.on("connect", () => {
        console.log("Socket connected");
      });

      socketRef.current.on("disconnect", () => {
        console.log("Socket disconnected");
      });

      socketRef.current.on("message", (message) => {
        console.log("New message received:", message);
      });
    }
  }, []);


  return (
    <></>
  )
};

