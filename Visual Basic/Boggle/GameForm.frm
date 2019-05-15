VERSION 5.00
Begin VB.Form GameForm 
   BackColor       =   &H00FF8080&
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Boggle"
   ClientHeight    =   6930
   ClientLeft      =   6750
   ClientTop       =   3975
   ClientWidth     =   6735
   BeginProperty Font 
      Name            =   "Times New Roman"
      Size            =   12
      Charset         =   0
      Weight          =   400
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   Icon            =   "GameForm.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   ScaleHeight     =   6930
   ScaleWidth      =   6735
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton FindWords 
      Caption         =   "Go"
      Height          =   375
      Left            =   2640
      TabIndex        =   45
      Top             =   6120
      Width           =   615
   End
   Begin VB.TextBox txtFind 
      Height          =   405
      Left            =   360
      TabIndex        =   44
      Text            =   "Text1"
      Top             =   6120
      Width           =   2175
   End
   Begin VB.Timer TimerTimer 
      Enabled         =   0   'False
      Interval        =   1000
      Left            =   3360
      Top             =   5520
   End
   Begin VB.CommandButton Clear 
      Caption         =   "Clear"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   405
      Left            =   120
      TabIndex        =   21
      Top             =   4560
      Width           =   855
   End
   Begin VB.CommandButton Submit 
      Caption         =   "Submit"
      Enabled         =   0   'False
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   405
      Left            =   1200
      TabIndex        =   20
      Top             =   4560
      Width           =   1095
   End
   Begin VB.ListBox WordList 
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   12.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   5760
      ItemData        =   "GameForm.frx":030A
      Left            =   4320
      List            =   "GameForm.frx":030C
      TabIndex        =   18
      Top             =   120
      Width           =   2295
   End
   Begin VB.Frame Frame1 
      BackColor       =   &H00FF8080&
      Height          =   3615
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   3975
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   28
         Left            =   3000
         TabIndex        =   16
         Top             =   2760
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   27
         Left            =   2040
         TabIndex        =   15
         Top             =   2760
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   26
         Left            =   1080
         TabIndex        =   14
         Top             =   2760
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   25
         Left            =   120
         TabIndex        =   13
         Top             =   2760
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   22
         Left            =   3000
         TabIndex        =   12
         Top             =   1920
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   21
         Left            =   2040
         TabIndex        =   11
         Top             =   1920
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   20
         Left            =   1080
         TabIndex        =   10
         Top             =   1920
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   19
         Left            =   120
         TabIndex        =   9
         Top             =   1920
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   16
         Left            =   3000
         TabIndex        =   8
         Top             =   1080
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   15
         Left            =   2040
         TabIndex        =   7
         Top             =   1080
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   14
         Left            =   1080
         TabIndex        =   6
         Top             =   1080
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   13
         Left            =   120
         TabIndex        =   5
         Top             =   1080
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   10
         Left            =   3000
         TabIndex        =   4
         Top             =   240
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   9
         Left            =   2040
         TabIndex        =   3
         Top             =   240
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   8
         Left            =   1080
         TabIndex        =   2
         Top             =   240
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   7
         Left            =   120
         TabIndex        =   1
         Top             =   240
         Width           =   855
      End
   End
   Begin VB.Frame Frame3 
      Caption         =   "Frame3"
      Height          =   975
      Left            =   1320
      TabIndex        =   23
      Top             =   1560
      Visible         =   0   'False
      Width           =   2655
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   35
         Left            =   0
         TabIndex        =   43
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   34
         Left            =   0
         TabIndex        =   42
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   33
         Left            =   0
         TabIndex        =   41
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   32
         Left            =   0
         TabIndex        =   40
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   31
         Left            =   0
         TabIndex        =   39
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   30
         Left            =   0
         TabIndex        =   38
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   29
         Left            =   0
         TabIndex        =   37
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   24
         Left            =   0
         TabIndex        =   36
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   23
         Left            =   0
         TabIndex        =   35
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   18
         Left            =   0
         TabIndex        =   34
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   17
         Left            =   0
         TabIndex        =   33
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   12
         Left            =   0
         TabIndex        =   32
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   11
         Left            =   0
         TabIndex        =   31
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   6
         Left            =   0
         TabIndex        =   30
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   5
         Left            =   0
         TabIndex        =   29
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   4
         Left            =   0
         TabIndex        =   28
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   3
         Left            =   0
         TabIndex        =   27
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   2
         Left            =   0
         TabIndex        =   26
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   1
         Left            =   0
         TabIndex        =   25
         Top             =   0
         Width           =   855
      End
      Begin VB.Label Letter 
         Alignment       =   2  'Center
         BackColor       =   &H00FFFFFF&
         BeginProperty Font 
            Name            =   "Times New Roman"
            Size            =   24
            Charset         =   0
            Weight          =   700
            Underline       =   0   'False
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         Height          =   735
         Index           =   0
         Left            =   0
         TabIndex        =   24
         Top             =   0
         Width           =   855
      End
   End
   Begin VB.Label Remaining 
      Alignment       =   2  'Center
      BackStyle       =   0  'Transparent
      Caption         =   "180"
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   3360
      TabIndex        =   22
      Top             =   5160
      Width           =   615
   End
   Begin VB.Label EnteredWord 
      BackColor       =   &H00FFFF00&
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   120
      TabIndex        =   19
      ToolTipText     =   "The selected word"
      Top             =   3960
      Width           =   3975
   End
   Begin VB.Label Status 
      BackStyle       =   0  'Transparent
      Caption         =   "Loading..."
      BeginProperty Font 
         Name            =   "Times New Roman"
         Size            =   14.25
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   120
      TabIndex        =   17
      Top             =   5160
      Width           =   2055
   End
End
Attribute VB_Name = "GameForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
Dim xxx As Long, yyy As Long, zzz As Long, aaa As Long, bbb As Long, ccc As Long, ddd As Long, eee As Long

Dim Words(1 To 26) As New Collection            'All the words listed
Dim Blocks(5 To 20, 1 To 6) As String           '(16 blocks, 6 letters per block)
Dim FirstLetter(1 To 26) As String              'The first letter of words (26 letters)
Dim a As Long
Dim b As String
Dim Clicked(0 To 35) As Boolean                 '(16 Blocks) Whether or not a letter has been clicked
Dim Points As Long                              'The number of points i have
Dim MyName As String
Dim MyNumber As Integer                         'My number in the game
Dim AllWords(1 To 8) As New Collection          'The words of all players
Dim ReadyOrNot(1 To 8) As Boolean

Private Sub Clear_Click()

EnteredWord.Caption = ""
Submit.Enabled = False
For xxx = 1 To 28
    Letter(xxx).Enabled = True
    Clicked(xxx) = False
Next xxx

End Sub

Private Sub FindWords_Click()

Dim Typed As String
Typed = txtFind.Text
Dim Searching As String
For xxx = 1 To 7
 For yyy = 1 To 7
  If yyy <> xxx Then
   For zzz = 1 To 7
    If zzz <> xxx And zzz <> yyy Then
     For aaa = 1 To 7
      If aaa <> xxx And aaa <> yyy And aaa <> zzz Then
       For bbb = 1 To 7
        If bbb <> aaa And bbb <> xxx And bbb <> yyy And bbb <> zzz Then
         For ccc = 1 To 7
          If ccc <> xxx And ccc <> yyy And ccc <> zzz And ccc <> aaa And ccc <> bbb Then
           For ddd = 1 To 7
            If ddd <> xxx And ddd <> yyy And ddd <> zzz And ddd <> aaa And ddd <> bbb And ddd <> ccc Then
             Searching = Mid$(Typed, xxx, 1) & Mid$(Typed, yyy, 1) & Mid$(Typed, zzz, 1) & Mid$(Typed, aaa, 1) & Mid$(Typed, bbb, 1) & Mid$(Typed, ccc, 1) & Mid$(Typed, ddd, 1)
             
                'Find First letter
                Dim FirstChar As String
                FirstChar = Mid$(Searching, 1, 1)
                For eee = 1 To 26
                    If FirstLetter(eee) = FirstChar Then a = eee
                Next eee
                
                For eee = 1 To Words(a).Count
                    If Searching = Words(a)(eee) Then
                        b = MsgBox(Searching, vbYesNo)
                        If b <> 6 Then Exit Sub
                    End If
                Next eee
            End If
           Next ddd
          End If
         Next ccc
        End If
       Next bbb
       DoEvents
       txtFind.Text = xxx & yyy & zzz & aaa
      End If
     Next aaa
    End If
   Next zzz
  End If
 Next yyy
Next xxx

End Sub

Private Sub Form_Load()

Me.Show
DoEvents

Randomize (Timer)

Open "Words.dat" For Input As #1
    For xxx = 1 To 26
        a = 0
        Do While a = 0
            Input #1, b
            If b = "#" Then
                a = 1
            Else
                Words(xxx).Add b
            End If
        Loop
    Next xxx
NoMoreWords:
Close #1

Open "Letters.dat" For Input As #1
    For xxx = 5 To 20
        For yyy = 1 To 6
            Input #1, Blocks(xxx, yyy)
        Next yyy
    Next xxx
Close #1

For xxx = 0 To 35
    Clicked(xxx) = False
Next xxx

FirstLetter(1) = "a"
FirstLetter(2) = "b"
FirstLetter(3) = "c"
FirstLetter(4) = "d"
FirstLetter(5) = "e"
FirstLetter(6) = "f"
FirstLetter(7) = "g"
FirstLetter(8) = "h"
FirstLetter(9) = "i"
FirstLetter(10) = "j"
FirstLetter(11) = "k"
FirstLetter(12) = "l"
FirstLetter(13) = "m"
FirstLetter(14) = "n"
FirstLetter(15) = "o"
FirstLetter(16) = "p"
FirstLetter(17) = "q"
FirstLetter(18) = "r"
FirstLetter(19) = "s"
FirstLetter(20) = "t"
FirstLetter(21) = "u"
FirstLetter(22) = "v"
FirstLetter(23) = "w"
FirstLetter(24) = "x"
FirstLetter(25) = "y"
FirstLetter(26) = "z"

Dim RandomNumber As Integer, RandomNumber2 As Integer
For xxx = 7 To 28
    'Which Block to choose from?
    a = 0
    Do While a = 0
        RandomNumber = Int(Rnd(100) * 16) + 5
        If Letter(xxx).Caption = "" Then
            RandomNumber2 = Int(Rnd(1) * 6) + 1
            Letter(xxx).Caption = Blocks(RandomNumber, RandomNumber2)
            a = 1
        End If
    Loop
    Randomize (Timer)
Next xxx

TimerTimer.Enabled = True
Status.Caption = "Ready"

End Sub

Private Sub Form_Unload(Cancel As Integer)

End

End Sub

Private Sub Letter_Click(Index As Integer)

If Letter(Index).Caption = "" Then Exit Sub

Status.Caption = "Loading..."
DoEvents

EnteredWord = EnteredWord & Letter(Index).Caption

'Enable other letters
For xxx = 7 To 28
    Letter(xxx).Enabled = False
Next xxx

Clicked(Index) = True
If Clicked(Index - 7) = False Then Letter(Index - 7).Enabled = True
If Clicked(Index - 6) = False Then Letter(Index - 6).Enabled = True
If Clicked(Index - 5) = False Then Letter(Index - 5).Enabled = True
If Clicked(Index - 1) = False Then Letter(Index - 1).Enabled = True
If Clicked(Index + 1) = False Then Letter(Index + 1).Enabled = True
If Clicked(Index + 5) = False Then Letter(Index + 5).Enabled = True
If Clicked(Index + 6) = False Then Letter(Index + 6).Enabled = True
If Clicked(Index + 7) = False Then Letter(Index + 7).Enabled = True

DoEvents

'Word or not?

'Find First letter
Dim FirstChar As String
FirstChar = Mid$(EnteredWord.Caption, 1, 1)
For xxx = 1 To 26
    If FirstLetter(xxx) = FirstChar Then a = xxx
Next xxx

For xxx = 1 To Words(a).Count
    If EnteredWord.Caption = Words(a)(xxx) Then
        'Has this word already been entered?
        For yyy = 0 To WordList.ListCount
            If EnteredWord.Caption = WordList.List(yyy) Then GoTo NoWord:
        Next yyy
        Status.Caption = "Ready"
        Submit.Enabled = True
        Submit.SetFocus
        Exit Sub
    End If
Next xxx
NoWord:
Submit.Enabled = False
Status.Caption = "Ready"
Clear.SetFocus

End Sub

Private Sub StartRound_Click()

Dim RandomNumber As Integer, RandomNumber2 As Integer
For xxx = 7 To 28
    'Which Block to choose from?
    a = 0
    Do While a = 0
        RandomNumber = Int(Rnd(100) * 16) + 5
        If Letter(xxx).Caption = "" Then
            RandomNumber2 = Int(Rnd(1) * 6) + 1
            Letter(xxx).Caption = Blocks(RandomNumber, RandomNumber2)
            a = 1
        End If
    Loop
    Randomize (Timer)
Next xxx

TimerTimer.Enabled = True
Clear.Enabled = True

End Sub

Private Sub Submit_Click()

WordList.AddItem EnteredWord.Caption
Dim WouldPoints As Integer

'How long is the word?
For xxx = 4 To 9
    If Mid$(EnteredWord.Caption, xxx, 1) = "" Then
        If xxx = 4 Or xxx = 5 Then Points = Points + 1
        If xxx = 6 Then WouldPoints = 2
        If xxx = 7 Then WouldPoints = 3
        If xxx = 8 Then WouldPoints = 5
        If xxx > 8 Then WouldPoints = 11
        xxx = 9
    End If
Next xxx

Clear_Click

End Sub

Private Sub TimerTimer_Timer()

Remaining.Caption = Remaining.Caption - 1
If Remaining.Caption = 0 Then
    For xxx = 1 To 16
        Letter(xxx).Caption = ""
    Next xxx
    Clear.Enabled = False
    Submit.Enabled = False
    Remaining.Caption = 180
    TimerTimer.Enabled = False
    
    MsgBox "You got " & WordList.ListCount & " words!  Your list will be saved in 'Word List.txt'.", vbInformation, "Boggle"
    Open "Words List.txt" For Output As #1
        For xxx = 0 To WordList.ListCount
            Print #1, WordList.List(xxx)
        Next xxx
    Close #1
    End
End If

If Remaining.Caption = 30 Then Remaining.ForeColor = vbBlue
If Remaining.Caption = 10 Then Remaining.ForeColor = vbRed
End Sub
