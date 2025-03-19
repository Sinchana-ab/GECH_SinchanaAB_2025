from django import forms
from . import models

class EmployeeForm(forms.ModelForm):
    class Meta:
        model = models.Employee
        fields = ["name", "email", "designation", "salary"]
        widgets = {
            "name": forms.TextInput(attrs={"class": "form-control", "placeholder": "Enter employee name"}),
            "email": forms.EmailInput(attrs={"class": "form-control", "placeholder": "Enter employee email"}),
            "designation": forms.TextInput(attrs={"class": "form-control", "placeholder": "Enter designation"}),
            "salary": forms.NumberInput(attrs={"class": "form-control", "placeholder": "Enter salary"}),
        }