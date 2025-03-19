from django.shortcuts import render, redirect
from . import models
from . import forms

# Create your views here.

def home(request):
    if request.method == "POST":
        emp_form = forms.EmployeeForm(request.POST)
        if emp_form.is_valid():
            emp_form.save()
            employee_form = forms.EmployeeForm()
    else:
        employee_form = forms.EmployeeForm()
    employees = models.Employee.objects.all()
    context = {
        "employees": employees,
        "employee_form": employee_form
    }
    return render(request, "home.html", context)

def about(request):
    return render(request, "about.html")

def contact(request):
    return render(request, "contact.html")

def edit_employee(request, id):
    employee = models.Employee.objects.get(id=id)
    if request.method == "POST":
        emp_form = forms.EmployeeForm(request.POST, instance=employee)
        if emp_form.is_valid():
            emp_form.save()
            return redirect("home")
    else:
        employee_form = forms.EmployeeForm(instance=employee)
    context = {"employee_form": employee_form}
    return render(request, "edit.html", context)

def delete_employee(request, id):
    employee = models.Employee.objects.get(id=id)
    if employee:
        employee.delete()
    return redirect("home")
