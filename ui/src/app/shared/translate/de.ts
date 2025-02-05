export const TRANSLATION = {
    General: {
        active: 'Aktiv',
        actualPower: 'E-Auto Beladung',
        apply: 'Übernehmen',
        autarchy: 'Autarkie',
        automatic: 'Automatisch',
        cancel: 'abbrechen',
        capacity: 'Kapazität',
        changeAccepted: 'Änderung übernommen',
        changeFailed: 'Änderung fehlgeschlagen',
        chargeDischarge: 'Be-/Entladung',
        chargePower: 'Beladung',
        componentCount: 'Anzahl Komponenten',
        componentInactive: 'Komponente ist inaktiv!',
        connectionLost: 'Verbindung unterbrochen. Versuche die Verbindung wiederherzustellen.',
        consumption: 'Verbrauch',
        cumulative: 'Kumulierte Werte',
        currentName: 'Aktueller Name',
        currentValue: 'Aktueller Wert',
        dateFormat: 'dd.MM.yyyy', // z.B. Englisch: yyyy-MM-dd (dd = Tag, MM = Monat, yyyy = Jahr)
        dateFormatShort: 'dd.MM',
        digitalInputs: 'Digitaleingänge',
        numberOfComponents: 'Anzahl der Komponenten',
        directConsumption: 'Direktverbrauch',
        dischargePower: 'Entladung',
        energyLimit: 'Energielimit',
        fault: 'Fehler',
        grid: 'Netz',
        gridBuy: 'Netzbezug',
        gridBuyAdvanced: 'Bezug',
        gridSell: 'Netzeinspeisung',
        gridSellAdvanced: 'Einspeisung',
        history: 'Historie',
        inactive: 'Inaktiv',
        info: 'Info',
        inputNotValid: 'Eingabe ungültig',
        insufficientRights: 'Unzureichende Rechte',
        live: 'Live',
        load: 'Last',
        manual: 'Anleitung',
        manually: 'Manuell',
        measuredValue: 'Gemessener Wert',
        mode: 'Modus',
        more: 'Mehr...',
        noValue: 'Kein Wert',
        off: 'Aus',
        offGrid: 'Keine Netzverbindung!',
        ok: 'ok',
        on: 'An',
        otherConsumption: 'Sonstiger',
        percentage: 'Prozent',
        periodFromTo: '{{value1}} - {{value2}}', // value1 = start date, value2 = end date
        phase: 'Phase',
        phases: 'Phasen',
        power: 'Leistung',
        production: 'Erzeugung',
        rename: 'Umbenennen',
        reportValue: 'Fehlerhafte Daten melden',
        reset: 'Zurücksetzen',
        search: 'Suchen',
        selfConsumption: 'Eigenverbrauch',
        soc: 'Ladezustand',
        state: 'Zustand',
        storageSystem: 'Speichersystem',
        systemState: 'Systemzustand',
        total: 'Gesamt',
        totalState: 'Gesamtstatus',
        warning: 'Warnung',
        Week: {
            monday: 'Montag',
            tuesday: 'Dienstag',
            wednesday: 'Mittwoch',
            thursday: 'Donnerstag',
            friday: 'Freitag',
            saturday: 'Samstag',
            sunday: 'Sonntag',
        },
        Month: {
            january: 'Januar',
            february: 'Februar',
            march: 'März',
            april: 'April',
            may: 'Mai',
            june: 'Juni',
            july: 'Juli',
            august: 'August',
            september: 'September',
            october: 'Oktober',
            november: 'November',
            december: 'Dezember',
        },
    },
    Menu: {
        aboutUI: 'Über OpenEMS',
        accessLevel: 'Zugriffslevel',
        edgeSettings: 'Einstellungen',
        generalSettings: 'Allgemeine Einstellungen',
        index: 'Übersicht',
        logout: 'Abmelden',
        menu: 'Menü',
        name: 'Name',
        overview: 'Alle Systeme',
        settings: 'Einstellungen',
        user: 'Benutzer',
    },
    Index: {
        allConnected: 'Alle Verbindungen hergestellt.',
        connectionInProgress: 'Verbindung wird aufgebaut...',
        connectionFailed: 'Verbindung zu {{value}} getrennt.', // value = name of websocket
        connectionSuccessful: 'Verbindung zu {{value}} hergestellt.', // value = name of websocket
        deviceOffline: 'Das Gerät ist nicht verbunden!',
        isOffline: 'OpenEMS ist offline!',
        loggedInAs: 'Angemeldet als:',
        toEnergymonitor: 'Zum Energiemonitor...',
        type: 'Typ:'
    },
    Login: {
        title: "Login",
        preamble: "Bitte geben Sie Ihr Passwort ein oder bestätigen Sie die Voreingabe um sich als Gast anzumelden.",
        passwordLabel: "Passwort",
        passwordReset: "Passwort zurücksetzen",
        authenticationFailed: "Authentifizierung fehlgeschlagen",
    },
    Register: {
        title: "Benutzer Account anlegen",
        segment: {
            user: "Benutzer",
            installer: "Installateur"
        },
        form: {
            companyName: "Firmenname",
            firstname: "Vorname",
            lastname: "Nachname",
            street: "Straße | Hausnummer",
            zip: "PLZ",
            city: "Ort",
            country: "Land",
            phone: "Telefonnummer",
            email: "E-Mail Adresse",
            password: "Passwort",
            confirmPassword: "Passwort wiederholen",
        },
        button: "Anlegen",
        errors: {
            requiredFields: "Bitte alle Felder ausfüllen",
            passwordNotEqual: "Passwörter sind nicht gleich"
        },
        success: "Registrierung erfolgreich"
    },
    Edge: {
        Index: {
            EmergencyReserve: {
                InfoForEmergencyReserveSlider: 'Durch Aktivieren kann eine Notstromreserve zwischen 5 % und 100 % der Batteriekapazität eingestellt werden.',
                emergencyReserve: 'Notstromreserve',
            },
            Energymonitor: {
                activePower: 'Ausgabeleistung',
                consumptionWarning: 'Verbrauch & unbekannte Erzeuger',
                gridMeter: 'Netzzähler',
                productionMeter: 'Erzeugungszähler',
                reactivePower: 'Blindleistung',
                storage: 'Speicher',
                storageCharge: 'Speicher-Beladung',
                storageDischarge: 'Speicher-Entladung',
                title: 'Energiemonitor',
            },
            Widgets: {
                autarchyInfo: 'Die Autarkie gibt an zu wie viel Prozent die aktuell genutzte Leistung durch Erzeugung und Speicherentladung gedeckt wird.',
                phasesInfo: 'Die Summe der einzelnen Phasen kann aus technischen Gründen geringfügig von der Gesamtsumme abweichen.',
                selfconsumptionInfo: 'Der Eigenverbrauch gibt an zu wie viel Prozent die aktuell erzeugte Leistung durch direkten Verbrauch und durch Speicherbeladung selbst genutzt wird.',
                twoWayInfoGrid: 'Negative Werte entsprechen Netzeinspeisung, Positive Werte entsprechen Netzbezug',
                InfoStorageForCharge: 'Negative Werte entsprechen Speicher Beladung',
                InfoStorageForDischarge: 'Positive Werte entsprechen Speicher Entladung',
                Channeltreshold: {
                    output: 'Ausgang'
                },
                Singlethreshold: {
                    above: 'Über',
                    behaviour: 'Verhalten',
                    below: 'Unter',
                    currentValue: 'Aktueller Wert',
                    dependendOn: 'Abhängig von',
                    minSwitchingTime: 'Mindestumschaltzeit',
                    moreThanMaxPower: 'Wert darf nicht niedriger als Maximalleistung des angesteuerten Geräts sein',
                    other: 'Sonstige',
                    relationError: 'Schwellenwert muss größer als die geschaltete Last sein',
                    switchedLoadPower: 'Geschaltete Last',
                    switchOffAbove: 'Ausschalten über',
                    switchOffBelow: 'Ausschalten unter',
                    switchOnAbove: 'Einschalten über',
                    switchOnBelow: 'Einschalten unter',
                    threshold: 'Schwellenwert',
                },
                DelayedSellToGrid: {
                    sellToGridPowerLimit: 'Beladung über',
                    continuousSellToGridPower: 'Entladung unter',
                    relationError: 'Beladungsgrenze muss größer der Entladungsgrenze sein',
                },
                Peakshaving: {
                    asymmetricInfo: 'Eingetragene Leistungswerte beziehen sich auf einzelne Phasen. Es wird auf die jeweils am stärksten belastete Phase ausgeregelt.',
                    endDate: 'End Datum',
                    endTime: 'Endzeit',
                    mostStressedPhase: 'Meist belastete Phase',
                    peakshaving: 'Lastspitzenkappung',
                    peakshavingPower: 'Entladung über',
                    recharge: 'Beladeleistung',
                    rechargePower: 'Beladung unter',
                    relationError: 'Entladungsgrenze muss größer oder gleich der Beladungsgrenze sein',
                    startDate: 'Start Datum',
                    startTime: 'Startzeit',
                    startTimeCharge: 'Start-Zeit Beladung',
                },
                GridOptimizedCharge: {
                    chargingDelayed: 'Beladung verzögert',
                    considerGridFeedInLimit: 'Maximale Netzeinspeisung berücksichtigen',
                    endTime: 'Endzeit',
                    endTimeDescription: 'Die Beladung erfolgt nicht mit der Maximalleistung für wenige Stunden, sondern gleichmäßig über einen längeren Zeitraum.',
                    endTimeDetailedDescription: 'Die Beladung erfolgt nicht mit der Maximalleistung für wenige Stunden, sondern gleichmäßig mit maximal {{value1}} bis {{value2}} Uhr.', // value1 = maximum charge, value2 = end time
                    endTimeLong: 'Endzeitpunkt der beschränkten Beladung',
                    expectedSoc: 'Erwarteter Ladezustand',
                    expectedSocWithoutSellToGridLimit: 'Ohne Vermeidung der maximalen Netzeinspeisung',
                    gridFeedInLimitationAvoided: 'Einspeisebegrenzung vermieden',
                    gridOptimizedChargeDisabled: 'Netzdienliche Beladung deaktiviert',
                    high: 'Hoch',
                    History: {
                        batteryChargeGridLimitDescription: 'Die maximale Netzeinspeisung, ab der die Batteriebeladung erhöht wird (soweit möglich), liegt unter der maximal erlaubten Netzeinspeisung, um ein vorzeitiges Abregeln der Erzeugung zu verhindern.',
                        priorityDescription: 'Die minimale Beladung hat eine höhere Priorität als die maximale Beladung',
                    },
                    low: 'Gering',
                    maximumCharge: 'Maximale Beladung',
                    maximumGridFeedIn: 'Maximal erlaubte Netzeinspeisung',
                    medium: 'Mittel',
                    minimumCharge: 'Minimale Beladung',
                    RiskDescription: {
                        LOW: {
                            functionDescription: 'Vergleichsweise frühzeitige Beladung des Speichers',
                            storageDescription: 'Höhere Wahrscheinlichkeit, dass der Speicher vollständig beladen wird',
                            pvCurtail: 'Geringere Wahrscheinlichkeit, dass die Abregelung der PV-Anlage vermieden wird',
                        },
                        MEDIUM: {
                            functionDescription: 'Vergleichsweise gleichmäßige Beladung des Speichers',
                            storageDescription: 'Hohe Wahrscheinlichkeit, dass der Speicher vollständig beladen wird',
                            pvCurtail: 'Hohe Wahrscheinlichkeit, dass die Abregelung der PV-Anlage vermieden wird',
                        },
                        HIGH: {
                            functionDescription: 'Vergleichsweise spätere Beladung des Speichers',
                            storageDescription: 'Geringere Wahrscheinlichkeit, dass der Speicher voll wird',
                            pvCurtail: 'Höhere Wahrscheinlichkeit, dass die Abregelung der PV-Anlage vermieden wird',
                        },
                    },
                    riskPropensity: 'Risikobereitschaft',
                    settingOnlyVisableForInstaller: 'Diese Einstellung ist nur für den Installateur sichtbar',
                    State: {
                        avoidLowCharging: 'Geringe Beladung vermieden',
                        chargeLimitActive: 'Beladelimit aktiv',
                        endTimeNotCalculated: 'Endzeitpunkt nicht berechnet',
                        gridFeedInLimitationIsAvoided: 'Einspeisebegrenzung wird vermieden',
                        noLimitActive: 'Kein Beladelimit aktiv',
                        noLimitPossible: 'Keine Begrenzung Möglich(Eingeschränkt durch Steuerungen mit höherer Priorität)',
                        notDefined: 'Nicht definiert',
                        passedEndTime: 'Endzeitpunkt der begrenzten Beladung überschritten',
                        storageAlreadyFull: 'Speicher bereits voll',
                    },
                    storageCapacity: 'Speicherkapazität (nur sichtbar für admin)'
                },
                CHP: {
                    highThreshold: 'Oberer Schwellenwert',
                    lowThreshold: 'Unterer Schwellenwert',
                },
                EVCS: {
                    activateCharging: 'Aktivieren der Ladesäule',
                    amountOfChargingStations: 'Anzahl der Ladestationen',
                    cable: 'Kabel',
                    cableNotConnected: 'Kabel ist nicht angeschlossen',
                    carFull: 'Auto ist voll',
                    chargeLimitReached: 'Ladelimit erreicht',
                    chargeTarget: 'Ladevorgabe',
                    charging: 'Beladung läuft',
                    chargingLimit: 'Lade-Begrenzung',
                    chargingPower: 'Ladeleistung',
                    chargingStation: 'Ladestation',
                    chargingStationCluster: 'Ladestation Cluster',
                    chargingStationDeactivated: 'Ladestation deaktiviert',
                    chargingStationPluggedIn: 'Ladestation eingesteckt',
                    chargingStationPluggedInEV: 'Ladestation + E-Auto eingesteckt',
                    chargingStationPluggedInEVLocked: 'Ladestation + E-Auto eingesteckt + gesperrt',
                    chargingStationPluggedInLocked: 'Ladestation eingesteckt + gesperrt',
                    clusterConfigError: 'Bei der Konfiguration des Evcs-Clusters ist ein Fehler aufgetreten',
                    currentCharge: 'Aktuelle Beladung',
                    energySinceBeginning: 'Energie seit Ladebeginn',
                    energyLimit: 'Energielimit',
                    enforceCharging: 'Erzwinge Beladung',
                    error: 'Fehler',
                    maxEnergyRestriction: 'Maximale Energie pro Ladevorgang begrenzen',
                    notAuthorized: 'Nicht authorisiert',
                    notCharging: 'Keine Beladung',
                    notReadyForCharging: 'Nicht bereit zur Beladung',
                    overviewChargingStations: 'Übersicht Ladestationen',
                    prioritization: 'Priorisierung',
                    readyForCharging: 'Bereit zur Beladung',
                    starting: 'Startet',
                    status: 'Status',
                    totalCharge: 'Gesamte Beladung',
                    totalChargingPower: 'Gesamte Ladeleistung',
                    unknown: 'Unbekannt',
                    unplugged: 'Ausgesteckt',
                    Administration: {
                        carAdministration: 'Autoverwaltung',
                        customCarInfo: 'Sollte das der Fall sein, kann ihr Auto nur ab einer bestimmten Leistung effizient beladen werden. Durch diesen Button wird das in Ihre Konfigurationsmöglichkeiten- sowie die in die automatische Beladung einberechnet.',
                        renaultZoe: 'Wird an dieser Ladesäule hauptsächlich ein Renault Zoe beladen?'
                    },
                    NoConnection: {
                        description: 'Es konnte keine Verbindung zur Ladestation aufgebaut werden.',
                        help1_1: 'Die IP der Ladesäule erscheint beim erneuten einschalten',
                        help1: 'Prüfen sie ob die Ladestation eingeschaltet und über das Netz erreichbar ist',
                    },
                    OptimizedChargeMode: {
                        info: 'In diesem Modus wird die Beladung des Autos an die aktuelle Produktion und den aktuellen Verbrauch angepasst.',
                        minChargePower: 'Minimale Ladestärke',
                        minCharging: 'Minimale Beladung garantieren',
                        minInfo: 'Falls verhindert werden soll, dass das Auto in der Nacht gar nicht lädt, kann eine minimale Aufladung festgelegt werden.',
                        name: 'Optimierte Beladung',
                        shortName: 'Automatisch',
                        ChargingPriority: {
                            car: 'E-Auto',
                            info: 'Je nach Priorisierung wird die ausgewählte Komponente zuerst beladen',
                            storage: 'Speicher',
                        }
                    },
                    ForceChargeMode: {
                        info: 'In diesem Modus wird die Beladung des Autos erzwungen, d.h. es wird immer garantiert, dass das Auto geladen wird, auch wenn die Ladesäule auf Netzstrom zugreifen muss.',
                        maxCharging: 'Maximale Ladeleistung',
                        maxChargingDetails: 'Falls das Auto den eingegebenen Maximalwert nicht laden kann, wird die Leistung automatisch begrenzt.',
                        name: 'Erzwungene Beladung',
                        shortName: 'Manuell',
                    }
                },
                Heatingelement: {
                    activeLevel: 'Aktives Level',
                    endtime: 'Endzeit',
                    energy: 'Energie',
                    heatingelement: 'Heizelement',
                    minimalEnergyAmount: 'Minimale Energiemenge',
                    minimumRunTime: 'Mindestlaufzeit',
                    priority: 'Priorität',
                    time: 'Zeit',
                    timeCountdown: 'Spätester Start',
                },
                HeatPump: {
                    aboveSoc: 'und über Ladezustand von',
                    belowSoc: 'und unter Ladezustand von',
                    gridBuy: 'Ab Netzbezug von',
                    gridSell: 'Ab Überschusseinspeisung von',
                    lock: 'Sperre',
                    moreThanMaxPower: 'Wert darf nicht niedriger als Maximalleistung der Wärmepumpe sein',
                    normalOperation: 'Normalbetrieb',
                    normalOperationShort: 'Normal',
                    relationError: 'Einschaltbefehl Überschusswert muss größer als Einschaltempfehlungswert sein',
                    switchOnCom: 'Einschaltbefehl',
                    switchOnComShort: 'Befehl',
                    switchOnRec: 'Einschaltempfehlung',
                    switchOnRecShort: 'Empfehlung',
                    undefined: 'Nicht definiert',
                },
                TimeOfUseTariff: {
                    currentTariff: 'Aktueller Bezugsstrompreis',
                    delayedDischarge: 'Entladung verzögert',
                    storageDischarge: 'Speicherentladung',
                    State: {
                        notStarted: 'Noch nicht gestartet',
                        delayed: 'Verzögert',
                        allowsDischarge: 'Freigegeben',
                        standby: 'Standby',
                    },
                },
            }
        },
        History: {
            activeDuration: 'Einschaltdauer',
            beginDate: 'Startdatum wählen',
            day: 'Tag',
            endDate: 'Enddatum wählen',
            export: 'Download als EXCEL-Datei',
            go: 'Los!',
            lastMonth: 'Letzter Monat',
            lastWeek: 'Letzte Woche',
            lastYear: 'Letztes Jahr',
            month: 'Monat',
            noData: 'keine Daten verfügbar',
            tryAgain: 'versuchen Sie es später noch einmal...',
            otherPeriod: 'Anderer Zeitraum',
            period: 'Zeitraum',
            selectedDay: '{{value}}',
            selectedPeriod: 'Gewählter Zeitraum: ',
            today: 'Heute',
            week: 'Woche',
            year: 'Jahr',
            yesterday: 'Gestern',
            sun: 'So',
            mon: 'Mo',
            tue: 'Di',
            wed: 'Mi',
            thu: 'Do',
            fri: 'Fr',
            sat: 'Sa',
            jan: 'Jan',
            feb: 'Feb',
            mar: 'Mär',
            apr: 'Apr',
            may: 'Mai',
            jun: 'Jun',
            jul: 'Jul',
            aug: 'Aug',
            sep: 'Sep',
            oct: 'Okt',
            nov: 'Nov',
            dec: 'Dez',
        },
        Config: {
            Index: {
                addComponents: 'Komponenten installieren',
                adjustComponents: 'Komponenten konfigurieren',
                bridge: 'Verbindungen und Geräte',
                controller: 'Anwendungen',
                dataStorage: 'Datenspeicher',
                executeSimulator: 'Simulationen ausführen',
                liveLog: 'Live Systemprotokoll',
                log: 'Log',
                manualControl: 'Manuelle Steuerung',
                renameComponents: 'Komponenten umbenennen',
                scheduler: 'Anwendungsplaner',
                simulator: 'Simulator',
                systemExecute: 'System-Befehl ausführen',
                systemProfile: 'Anlagenprofil',
                alerting: 'Benachrichtigung',
            },
            More: {
                manualCommand: 'Manueller Befehl',
                manualpqPowerSpecification: 'Leistungsvorgabe',
                manualpqReset: 'Zurücksetzen',
                manualpqSubmit: 'Übernehmen',
                refuInverter: 'REFU Wechselrichter',
                refuStart: 'Starten',
                refuStartStop: 'Wechselrichter starten/stoppen',
                refuStop: 'Stoppen',
                send: 'Senden',
            },
            Scheduler: {
                always: 'Immer',
                class: 'Klasse:',
                contact: 'Das sollte nicht passieren. Bitte kontaktieren Sie <a href=\'mailto:{{value}}\'>{{value}}</a>.',
                newScheduler: 'Neuer Scheduler...',
                notImplemented: 'Formular nicht implementiert: ',
            },
            Log: {
                automaticUpdating: 'Automatische Aktualisierung',
                level: 'Level',
                message: 'Nachricht',
                source: 'Quelle',
                timestamp: 'Zeitpunkt',
            },
            Controller: {
                app: 'Anwendung:',
                internallyID: 'Interne ID:',
                priority: 'Priorität:',
            },
            Bridge: {
                newConnection: 'Neue Verbindung...',
                newDevice: 'Neues Gerät...',
            },
            Alerting: {
                activate: 'Aktivieren',
                delay: 'Verzögerung',
                save: 'Speichern',
                options: {
                    15: '15 Minuten',
                    60: '1 Stunde',
                    1440: '1 Tag'
                },
                toast: {
                    success: 'Einstellungen übernommen',
                    error: 'Fehler beim Laden der Einstellungen'
                },
            },
            App: {
                header: 'Der App-Manager befindet sich aktuell in einer ersten Testversion. Falls nicht alle Apps angezeigt werden, muss evtl. die FEMS Version geupdatet werden.',
                installed: 'Installiert',
                available: 'Verfügbar',
                incompatible: 'Inkompatibel',
                buyApp: 'App kaufen',
                modifyApp: 'App bearbeiten',
                createApp: 'App installieren',
                deleteApp: 'App entfernen',
                updateApp: 'App aktualisieren',
                errorInstallable: 'Installierungs fehler',
                errorCompatible: 'Kompatibilitäts fehler',
            },
        },
        Service: {
            entireSystem: "Gesamtsystem",
            Cell: {
                voltages: "Zellspannungen",
                temperatures: "Zelltemperaturen",
                insulation: "Isolation",
            }
        }
    },
    About: {
        build: "Dieser Build",
        contact: "Für Rückfragen und Anregungen zum System, wenden Sie sich bitte an unser Team unter <a href=\"mailto:{{value}}\">{{value}}</a>.",
        currentDevelopments: "Aktuelle Entwicklungen",
        developed: "Diese Benutzeroberfläche wird als Open-Source-Software entwickelt.",
        faq: "Häufig gestellte Fragen (FAQ)",
        language: "Sprache wählen:",
        openEMS: "Mehr zu OpenEMS",
        patchnotes: "Änderungen im Monitoring zu diesem Build",
        ui: "Benutzeroberfläche für OpenEMS",
    },
    Notifications: {
        authenticationFailed: 'Keine Verbindung: Authentifizierung fehlgeschlagen.',
        closed: 'Verbindung beendet.',
        failed: 'Verbindungsaufbau fehlgeschlagen.',
        loggedIn: 'Angemeldet.',
        loggedInAs: 'Angemeldet als Benutzer \'{{value}}\'.', // value = username
    },
    Role: {
        guest: 'Gast',
        owner: 'Eigentümer',
        installer: 'Installateur',
        admin: 'Administrator',
    }
}
