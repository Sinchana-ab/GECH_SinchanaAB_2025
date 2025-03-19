from django.urls import path
from employee_crud import views

urlpatterns = [
    path("", views.home, name="home"),
    path("about/", views.about),
    path("contact/", views.contact),
    path("edit/<int:id>", views.edit_employee, name="edit"),
    path("delete/<int:id>", views.delete_employee, name="delete"),
]