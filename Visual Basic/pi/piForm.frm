VERSION 5.00
Begin VB.Form piForm 
   Caption         =   "Irrational Numbers!"
   ClientHeight    =   5490
   ClientLeft      =   4290
   ClientTop       =   2685
   ClientWidth     =   6510
   BeginProperty Font 
      Name            =   "Times New Roman"
      Size            =   12
      Charset         =   0
      Weight          =   400
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   Icon            =   "piForm.frx":0000
   LinkTopic       =   "Form1"
   ScaleHeight     =   5490
   ScaleWidth      =   6510
   Begin VB.OptionButton Option1 
      Caption         =   "GR"
      Height          =   375
      Index           =   2
      Left            =   2280
      TabIndex        =   15
      Top             =   120
      Width           =   735
   End
   Begin VB.OptionButton Option1 
      Caption         =   "e"
      Height          =   375
      Index           =   1
      Left            =   1440
      TabIndex        =   14
      Top             =   120
      Width           =   735
   End
   Begin VB.OptionButton Option1 
      Caption         =   "pi"
      Height          =   375
      Index           =   0
      Left            =   600
      TabIndex        =   13
      Top             =   120
      Value           =   -1  'True
      Width           =   735
   End
   Begin VB.TextBox cmdpi 
      Enabled         =   0   'False
      Height          =   405
      Left            =   5520
      TabIndex        =   9
      Top             =   120
      Width           =   735
   End
   Begin VB.Frame ButtonFrame 
      BorderStyle     =   0  'None
      Caption         =   "Frame1"
      Height          =   1095
      Left            =   120
      TabIndex        =   1
      Top             =   3840
      Width           =   6375
      Begin VB.ComboBox cboWidth 
         Height          =   405
         ItemData        =   "piForm.frx":030A
         Left            =   3720
         List            =   "piForm.frx":0317
         TabIndex        =   12
         Text            =   "50"
         Top             =   0
         Width           =   1095
      End
      Begin VB.CommandButton Command2 
         Caption         =   "Start Over"
         Height          =   375
         Left            =   4200
         TabIndex        =   8
         Top             =   600
         Width           =   1215
      End
      Begin VB.CommandButton Command1 
         Caption         =   "Exit"
         Height          =   375
         Left            =   5520
         TabIndex        =   7
         ToolTipText     =   "Exit out of the program"
         Top             =   600
         Width           =   735
      End
      Begin VB.Label Label5 
         Alignment       =   1  'Right Justify
         Caption         =   "Number of digits to display on each line:  "
         Height          =   375
         Left            =   0
         TabIndex        =   11
         Top             =   0
         Width           =   3735
      End
      Begin VB.Label Label2 
         Alignment       =   1  'Right Justify
         Caption         =   "Digits: "
         Height          =   375
         Left            =   0
         TabIndex        =   5
         Top             =   600
         Width           =   855
      End
      Begin VB.Label lblCurrent 
         Caption         =   "0"
         BeginProperty DataFormat 
            Type            =   1
            Format          =   "#,##0"
            HaveTrueFalseNull=   0
            FirstDayOfWeek  =   0
            FirstWeekOfYear =   0
            LCID            =   1033
            SubFormatType   =   1
         EndProperty
         Height          =   375
         Left            =   960
         TabIndex        =   4
         ToolTipText     =   "The total number of digits you have got right or wrong"
         Top             =   600
         Width           =   1215
      End
      Begin VB.Label Label3 
         Alignment       =   1  'Right Justify
         Caption         =   "Mistakes: "
         Height          =   375
         Left            =   2160
         TabIndex        =   3
         Top             =   600
         Width           =   975
      End
      Begin VB.Label Mistakes 
         Caption         =   "0"
         BeginProperty DataFormat 
            Type            =   1
            Format          =   "#,##0"
            HaveTrueFalseNull=   0
            FirstDayOfWeek  =   0
            FirstWeekOfYear =   0
            LCID            =   1033
            SubFormatType   =   1
         EndProperty
         Height          =   375
         Left            =   3240
         TabIndex        =   2
         ToolTipText     =   "The number of mistakes you have made."
         Top             =   600
         Width           =   1095
      End
   End
   Begin VB.TextBox txtpi 
      Height          =   3135
      Left            =   120
      Locked          =   -1  'True
      MultiLine       =   -1  'True
      ScrollBars      =   2  'Vertical
      TabIndex        =   0
      ToolTipText     =   "Digits done thus far"
      Top             =   600
      Width           =   6255
   End
   Begin VB.Label Label1 
      Alignment       =   1  'Right Justify
      Caption         =   "Type in your next digit:  "
      Height          =   375
      Left            =   3120
      TabIndex        =   10
      Top             =   120
      Width           =   2295
   End
   Begin VB.Label Label4 
      Caption         =   "3."
      Height          =   375
      Left            =   120
      TabIndex        =   6
      Top             =   120
      Width           =   255
   End
End
Attribute VB_Name = "piForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim EDigits
Dim PiDigits
Dim GRDigits
Dim CurrentDigit
Dim Allowed As Boolean

Private Sub cboWidth_Click()
Attribute cboWidth_Click.VB_Description = "Changes the width of the screen so that it displays 50, 100, or 150 digits"

If cboWidth.Text = "50" Then
    Me.Width = 6760
Else
    If cboWidth.Text = "100" Then
        Me.Width = 12375
    Else
        Me.Width = 18345
    End If
End If

End Sub

Private Sub cmdpi_Change()
Attribute cmdpi_Change.VB_Description = "The next number"

If Allowed = True Then
Else
    Allowed = True
    Exit Sub
End If

'Do next digit
If Option1(0).Value = True Then
    Allowed = False
    txtpi.Text = txtpi.Text & Mid$(PiDigits, CurrentDigit, 1)
    If cmdpi.Text <> Mid$(PiDigits, CurrentDigit, 1) Then
        Beep
        Mistakes.Caption = Mistakes.Caption + 1
    End If
Else
    If Option1(1).Value = True Then
        Allowed = False
        txtpi.Text = txtpi.Text & Mid$(EDigits, CurrentDigit, 1)
        If cmdpi.Text <> Mid$(EDigits, CurrentDigit, 1) Then
            Beep
            Mistakes.Caption = Mistakes.Caption + 1
        End If
    Else
        Allowed = False
        txtpi.Text = txtpi.Text & Mid$(GRDigits, CurrentDigit, 1)
        If cmdpi.Text <> Mid$(GRDigits, CurrentDigit, 1) Then
            Beep
            Mistakes.Caption = Mistakes.Caption + 1
        End If
    End If
End If

cmdpi.Text = ""
CurrentDigit = CurrentDigit + 1
lblCurrent = lblCurrent + 1

End Sub

Private Sub Command1_Click()
End
End Sub

Private Sub Command2_Click()

txtpi.Text = ""
lblCurrent.Caption = 0
CurrentDigit = 1
Mistakes.Caption = 0
cmdpi.SetFocus

End Sub

Private Sub Form_Load()
Attribute Form_Load.VB_Description = "Load pi, e, and GR"

'Load pi
Open "pi.txt" For Input As #1
    Input #1, a
    PiDigits = Mid$(a, 2, 100000)
Close #1

'Load GR
Open "Golden Ratio.txt" For Input As #1
    Input #1, a
    GRDigits = Mid$(a, 2, 100000)
Close #1

'Load e
Open "e.txt" For Input As #1
    Input #1, a
    EDigits = Mid$(a, 2, 100000)
Close #1
CurrentDigit = 1
Allowed = True


cmdpi.Enabled = True
Me.Width = 6760

Me.Show

End Sub

Private Sub Form_Resize()
Attribute Form_Resize.VB_Description = "Set control posistions"

If Me.WindowState = 1 Then Exit Sub

If Me.Width < 6760 Then Me.Width = 6760
If Me.Height < 5550 Then Me.Height = 5650

With txtpi
    .Width = Me.Width - 360
    .Height = Me.Height - ButtonFrame.Height - 1120
    ButtonFrame.Top = .Height + 600
End With

Me.Show
cmdpi.SetFocus

End Sub

Private Sub Option1_Click(Index As Integer)
Attribute Option1_Click.VB_Description = "Changes the number"

If Index = 0 Then
    Label4.Caption = "3."
Else
    If Index = 1 Then
        Label4.Caption = "2."
    Else
        Label4.Caption = "1."
    End If
End If

txtpi.Text = ""
lblCurrent.Caption = 0
CurrentDigit = 1
Mistakes.Caption = 0
cmdpi.SetFocus

End Sub
