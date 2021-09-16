package com.makichanov.bassistant.controller.servlet;

import java.io.*;

import com.makichanov.bassistant.controller.command.ActionCommand;
import com.makichanov.bassistant.controller.command.CommandProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.makichanov.bassistant.controller.command.RequestParameter.*;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getParameter(COMMAND);
        CommandProvider commandProvider = CommandProvider.getInstance();
        ActionCommand command = commandProvider.getCommand(commandName);
        String page = command.execute(request);
        if (page != null) {
            getServletContext().getRequestDispatcher(page).forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }
}