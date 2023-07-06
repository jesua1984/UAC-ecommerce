function getStatusColor(status) {
  switch (status) {
    case "creada":
      return "color-blue";
    case "facturada":
      return "color-orange";
    case "enviada":
      return "color-purple";
    case "entregada-cobrada":
      return "color-green";
    case "cancelada":
      return "color-red";
    default:
      return "";
  }
}


